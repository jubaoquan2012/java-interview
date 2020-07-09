package com.interview.javabinterview.spring.springboot.threadpool.impl;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutor extends AbstractExecutorService {

    /**
     * ctl是对线程池的运行状态和线程池中有效线程的数量进行控制的一个字段， 它包含两部分的信息:
     *      线程池的运行状态 (runState) 和线程池内有效线程的数量 (workerCount)，
     *      这里可以看到，使用了Integer类型来保存，高3位保存runState，低29位保存workerCount。COUNT_BITS 就是29，
     *      CAPACITY就是1左移29位减1（29个1），这个常量表示workerCount的上限值，大约是5亿。
     */
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    private static final int COUNT_BITS = Integer.SIZE - 3;//32 -3 = 29

    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;//

    private static final int SHUTDOWN = 0 << COUNT_BITS;//线程池此状态时，不接收新任务，但能处理已添加的任务

    private static final int STOP = 1 << COUNT_BITS;//线程池处在STOP状态时，不接收新任务，不处理已添加的任务，并且会中断正在处理的任务。

    private static final int TIDYING = 2 << COUNT_BITS;//当所有的任务已终止，ctl记录的”任务数量”为0，线程池会变为TIDYING状态

    private static final int TERMINATED = 3 << COUNT_BITS;//线程池彻底终止，就变成TERMINATED状态。

    // Packing and unpacking ctl
    private static int runStateOf(int c) { return c & ~CAPACITY; }

    private static int workerCountOf(int c) { return c & CAPACITY; }

    private static int ctlOf(int rs, int wc) { return rs | wc; }

    private static boolean runStateLessThan(int c, int s) {
        return c < s;
    }

    private static boolean runStateAtLeast(int c, int s) {
        return c >= s;
    }

    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }

    private boolean compareAndIncrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect + 1);
    }

    private boolean compareAndDecrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect - 1);
    }

    private void decrementWorkerCount() {
        do {
        } while (!compareAndDecrementWorkerCount(ctl.get()));
    }

    private final BlockingQueue<Runnable> workQueue;

    private final ReentrantLock mainLock = new ReentrantLock();

    private final HashSet<Worker> workers = new HashSet<Worker>();

    private final Condition termination = mainLock.newCondition();

    private int largestPoolSize;

    private long completedTaskCount;

    private volatile ThreadFactory threadFactory;

    private volatile RejectedExecutionHandler handler;

    private volatile long keepAliveTime;

    private volatile boolean allowCoreThreadTimeOut;

    private volatile int corePoolSize;

    private volatile int maximumPoolSize;

    private static final RejectedExecutionHandler defaultHandler =
            new AbortPolicy();

    private static final RuntimePermission shutdownPerm =
            new RuntimePermission("modifyThread");

    /* The context to be used when executing the finalizer, or null. */
    private final AccessControlContext acc;

    private final class Worker extends AbstractQueuedSynchronizer implements Runnable {

        private static final long serialVersionUID = 6138294804551838833L;

        //用来封装worker的线程，线程池中真正运行的线程,通过线程工厂创建而来
        final Thread thread;

        //worker所对应的第一个任务，可能为空
        Runnable firstTask;

        //记录当前线程完成的任务数
        volatile long completedTasks;

        Worker(Runnable firstTask) {
            //设置AQS的state为-1，在执行runWorker()方法之前阻止线程中断
            setState(-1);
            //初始化第一个任务
            this.firstTask = firstTask;
            //利用指定的线程工厂创建一个线程，注意，参数是Worker实例本身this
            //也就是当执行start方法启动线程thread时，真正执行的是Worker类的run方法
            this.thread = getThreadFactory().newThread(this);
        }

        public void run() {
            runWorker(this);
        }

        protected boolean isHeldExclusively() {
            return getState() != 0;
        }

        protected boolean tryAcquire(int unused) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        protected boolean tryRelease(int unused) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        public void lock() { acquire(1); }

        public boolean tryLock() { return tryAcquire(1); }

        public void unlock() { release(1); }

        public boolean isLocked() { return isHeldExclusively(); }

        void interruptIfStarted() {
            Thread t;
            //AQS状态大于等于0，worker对应的线程不为null，且该线程没有被中断
            if (getState() >= 0 && (t = thread) != null && !t.isInterrupted()) {
                try {
                    t.interrupt();
                } catch (SecurityException ignore) {
                }
            }
        }
    }

    private void advanceRunState(int targetState) {
        for (; ; ) {
            int c = ctl.get();
            if (runStateAtLeast(c, targetState) ||
                    ctl.compareAndSet(c, ctlOf(targetState, workerCountOf(c)))) {
                break;
            }
        }
    }

    /**
     *
     * tryTerminate()方法的作用是尝试终止线程池，它会在所有可能终止线程池的地方被调用，满足终止线程池的条件有两个：
     * 首先，线程池状态为STOP,或者为SHUTDOWN且任务缓存队列为空；其次，工作线程数量为0。
     *
     * 满足了上述两个条件之后，tryTerminate()方法获取全局锁，设置线程池运行状态为TIDYING，
     * 之后执行terminated()钩子方法，最后设置线程池状态为TERMINATED。
     *
     * 至此，线程池运行状态变为TERMINATED，工作线程数量为0，workers已清空，且workQueue也已清空，
     * 所有线程都执行结束，线程池的生命周期到此结束。
     */
    final void tryTerminate() {
        for (; ; ) {
            int c = ctl.get();
            if (isRunning(c) ||//线程池的运行状态为RUNNING
                    runStateAtLeast(c, TIDYING) ||//线程池的运行状态大于等于TIDYING
                    (runStateOf(c) == SHUTDOWN && !workQueue.isEmpty())) {//线程池的运行状态为SHUTDOWN且阻塞队列不为空
                //不能终止，直接返回
                return;
            }
            //只有当线程池的运行状态为STOP，或线程池运行状态为SHUTDOWN且阻塞队列为空时，可以执行到这里
            //如果线程池工作线程的数量不为0
            if (workerCountOf(c) != 0) { // Eligible to terminate
                interruptIdleWorkers(ONLY_ONE);
                return;
            }

            //只有当线程池工作线程的数量为0时可以执行到这里
            final ReentrantLock mainLock = this.mainLock;
            //获取全局锁
            mainLock.lock();
            try {
                if (ctl.compareAndSet(c, ctlOf(TIDYING, 0))) {//CAS操作设置线程池运行状态为TIDYING，工作线程数量为0
                    try {
                        //执行terminated()钩子方法
                        terminated();
                    } finally {
                        //设置线程池运行状态为TERMINATED，工作线程数量为0
                        ctl.set(ctlOf(TERMINATED, 0));
                        //唤醒在termination条件上等待的所有线程
                        termination.signalAll();
                    }
                    return;
                }
            } finally {
                mainLock.unlock();
            }
            // else retry on failed CAS
        }
    }

    private void checkShutdownAccess() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(shutdownPerm);
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                for (Worker w : workers) {
                    security.checkAccess(w.thread);
                }
            } finally {
                mainLock.unlock();
            }
        }
    }

    private void interruptWorkers() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (Worker w : workers) {
                w.interruptIfStarted();
            }
        } finally {
            mainLock.unlock();
        }
    }

    private void interruptIdleWorkers(boolean onlyOne) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (Worker w : workers) {
                Thread t = w.thread;
                if (!t.isInterrupted() && w.tryLock()) {
                    try {
                        t.interrupt();
                    } catch (SecurityException ignore) {
                    } finally {
                        w.unlock();
                    }
                }
                if (onlyOne) {
                    break;
                }
            }
        } finally {
            mainLock.unlock();
        }
    }

    private void interruptIdleWorkers() {
        interruptIdleWorkers(false);
    }

    private static final boolean ONLY_ONE = true;

    final void reject(Runnable command) {
        handler.rejectedExecution(command, this);
    }

    void onShutdown() {
    }

    final boolean isRunningOrShutdown(boolean shutdownOK) {
        int rs = runStateOf(ctl.get());
        return rs == RUNNING || (rs == SHUTDOWN && shutdownOK);
    }

    private List<Runnable> drainQueue() {
        BlockingQueue<Runnable> q = workQueue;
        ArrayList<Runnable> taskList = new ArrayList<Runnable>();
        q.drainTo(taskList);
        if (!q.isEmpty()) {
            for (Runnable r : q.toArray(new Runnable[0])) {
                if (q.remove(r)) {
                    taskList.add(r);
                }
            }
        }
        return taskList;
    }

    /**
     * addWorker方法的主要工作是在线程池中创建一个新的线程并执;
     * firstTask参数 用于指定新增的线程执行的第一个任务;
     * core参数为:
     *      true  表示在新增线程时会判断当前活动线程数是否少于corePoolSize，
     *      false 表示新增线程前需要判断当前活动线程数是否少于maximumPoolSize
     */
    private boolean addWorker(Runnable firstTask, boolean core) {
        retry:
        for (; ; ) {
            int c = ctl.get();
            int rs = runStateOf(c);
            /*
             * 这个if判断
             * 如果rs >= SHUTDOWN，则表示此时不再接收新任务；
             * 1. rs > SHUTDOWN 直接返回false
             * 2. rs = SHUTDOWN 接着判断以下3个条件，只要有1个不满足，则返回false：
             *      (1). rs == SHUTDOWN，这时表示关闭状态，不再接受新提交的任务，但却可以继续处理阻塞队列中已保存的任务
             *      (2). firsTask  为空
             *      (3). 阻塞队列  不为空
             *
             *      具体分析:rs == SHUTDOWN 已经满足,看剩下的 (2) 和 (3)条件
             *          rs == SHUTDOWN 情况下:
             *           firstTask 不为空 返回false: 不会接受新提交的任务
             *           workQueue 为空， 返回false: 因为队列中已经没有任务了，不需要再添加线程了
             */
            if (rs >= SHUTDOWN &&
                    !(rs == SHUTDOWN && firstTask == null && !workQueue.isEmpty())) {
                return false;
            }
            for (; ; ) {
                // 获取线程池中线程数量
                int wc = workerCountOf(c);
                // core参数, 如果core是true跟最大核心线程数比较，否则跟最大线程数比较
                if (wc >= CAPACITY || wc >= (core ? corePoolSize : maximumPoolSize)) {
                    return false;
                }
                //CAS操作递增workCount
                //如果成功，那么创建线程前的所有条件校验都满足了，准备创建线程执行任务，退出retry循环
                //如果失败，说明有其他线程也在尝试往线程池中创建线程(往线程池提交任务可以是并发的)，则继续往下执行
                if (compareAndIncrementWorkerCount(c)) {
                    break retry;
                }
                c = ctl.get();
                //检测当前线程状态如果发生了变化，则继续回到retry，重新开始循环
                if (runStateOf(c) != rs) {
                    continue retry;
                }
            }
        }
        //走到这里，说明我们已经成功的将线程数+1了，但是真正的线程还没有被添加
        boolean workerStarted = false;
        boolean workerAdded = false;
        Worker w = null;
        try {
            w = new Worker(firstTask);
            //取出worker中的线程对象,Worker的构造方法会调用ThreadFactory来创建一个新的线程
            final Thread t = w.thread;
            if (t != null) {
                //获取全局锁, 并发的访问线程池workers对象必须加锁,持有锁的期间线程池也不会被关闭
                final ReentrantLock mainLock = this.mainLock;
                mainLock.lock();
                try {
                    int rs = runStateOf(ctl.get());
                    // RUNNING状态 || SHUTDONW状态(不会再添加新的任务，但还是会执行workQueue中的任务)
                    if (rs < SHUTDOWN ||
                            (rs == SHUTDOWN && firstTask == null)) {
                        //线程只能被start一次
                        if (t.isAlive()) {
                            throw new IllegalThreadStateException();
                        }
                        //将新启动的线程添加到线程池中,(workers是一个HashSet)
                        workers.add(w);
                        int s = workers.size();
                        if (s > largestPoolSize) {// largestPoolSize记录着线程池中出现过的最大线程数量
                            largestPoolSize = s;
                        }
                        workerAdded = true;
                    }
                } finally {
                    mainLock.unlock();
                }
                // 启动新添加的线程，这个线程首先执行firstTask，然后不停的从队列中取任务执行
                if (workerAdded) {
                    //执行ThreadPoolExecutor的runWoker方法
                    t.start();
                    workerStarted = true;
                }
            }
        } finally {
            // 线程启动失败，则从wokers中移除w并递减 wokerCount
            if (!workerStarted) {
                addWorkerFailed(w);
            }
        }
        return workerStarted;
    }

    private void addWorkerFailed(Worker w) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            if (w != null) {
                workers.remove(w);
            }
            decrementWorkerCount();
            tryTerminate();
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * processWorkerExit()方法负责执行结束线程的一些清理工作，下面分析一下其实现。
     * @param w
     * @param completedAbruptly
     */
    private void processWorkerExit(Worker w, boolean completedAbruptly) {
        //如果用户任务执行过程中发生了异常，则需要递减workerCount
        if (completedAbruptly) {// If abrupt, then workerCount wasn't adjusted
            decrementWorkerCount();
        }

        final ReentrantLock mainLock = this.mainLock;
        //获取全局锁
        mainLock.lock();
        try {
            //将worker完成任务的数量累加到总的完成任务数中
            completedTaskCount += w.completedTasks;
            //从workers集合中移除该worker
            workers.remove(w);
        } finally {
            mainLock.unlock();
        }
        //尝试终止线程池
        tryTerminate();

        int c = ctl.get();
        if (runStateLessThan(c, STOP)) {
            if (!completedAbruptly) {//如果用户任务执行过程中发生了异常，则直接调用addWorker()方法创建线程
                int min = allowCoreThreadTimeOut ? 0 : corePoolSize; //是否允许核心线程超时
                if (min == 0 && !workQueue.isEmpty()) {   //允许核心超时并且workQueue阻塞队列不为空，那线程池中至少有一个工作线程
                    min = 1;
                    //如果工作线程数量workerCount大于等于核心池大小corePoolSize，
                    //或者允许核心超时并且workQueue阻塞队列不为空时，线程池中至少有一个工作线程，直接返回
                }

                if (workerCountOf(c) >= min) {
                    return; // replacement not needed
                }
                //若不满足上述条件，则调用addWorker()方法创建线程
            }
            //创建新的线程取代当前线程
            addWorker(null, false);
        }
    }

    /**
     * 1.线程池处于RUNNING状态，阻塞队列不为空，返回成功获取的task对象
     *
     * 2.线程池处于SHUTDOWN状态，阻塞队列不为空，返回成功获取的task对象
     *
     * 3.线程池状态大于等于STOP，返回null，回收线程
     *
     * 4.线程池处于SHUTDOWN状态，并且阻塞队列为空，返回null，回收线程
     *
     * 5.worker数量大于maximumPoolSize，返回null，回收线程
     *
     * 6.线程空闲时间超时，返回null，回收线程
     * @return
     */
    private Runnable getTask() {
        //标识当前线程是否超时未能获取到task对象
        boolean timedOut = false; // Did the last poll() time out?

        for (; ; ) {
            int c = ctl.get();
            int rs = runStateOf(c);

            //检查线程池的状态，如果已经是STOP及以上的状态，或者已经SHUTDOWN，
            // 队列也是空的时候，直接return null，并将Worker数量-1
            if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
                // 递减workerCount值
                decrementWorkerCount();
                return null;
            }
            int wc = workerCountOf(c);
            /**
             * 注意这里的allowCoreThreadTimeOut参数，字面意思是否允许核心线程超时，
             * 即如果我们设置为false，那么只有当线程数wc大于corePoolSize的时候才会超时
             * 更直接的意思就是，如果设置allowCoreThreadTimeOut为false，
             * 那么线程池在达到corePoolSize个工作线程之前，不会让闲置的工作线程退出
             */
            boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;
            //确认超时，将Worker数-1，然后返回
            if ((wc > maximumPoolSize || (timed && timedOut))
                    && (wc > 1 || workQueue.isEmpty())) {//workerCount大于1或者阻塞队列为空（在阻塞队列不为空时，需要保证至少有一个工作线程）
                if (compareAndDecrementWorkerCount(c)) {
                    return null;
                }
                continue;
            }

            try {
                //如果允许超时回收，则调用阻塞队列的poll()，只在keepAliveTime时间内等待获取任务，一旦超过则返回null
                //否则调用take()，如果队列为空，线程进入阻塞状态，无限时等待任务，直到队列中有可取任务或者响应中断信号退出
                Runnable r = timed
                        ? workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
                        workQueue.take();
                //若task不为null，则返回成功获取的task对象
                if (r != null) {
                    return r;
                }
                // 若返回task为null，表示线程空闲时间超时，则设置timeOut为true
                timedOut = true; // 超时
            } catch (InterruptedException retry) {
                //如果此worker发生了中断，采取的方案是重试，没有超时
                //在哪些情况下会发生中断？调用setMaximumPoolSize()，shutDown()，shutDownNow()
                timedOut = false; // 线程被中断重试
            }
        }
    }

    /**
     * 1.运行第一个任务firstTask之后，循环调用getTask()方法获取任务,不断从任务缓存队列获取任务并执行；
     *
     * 2.获取到任务之后就对worker对象加锁，保证线程在执行任务的过程中不会被中断，任务执行完会释放锁；
     *
     * 3.在执行任务的前后，可以根据业务场景重写beforeExecute()和afterExecute()等Hook方法；
     *
     * 4.执行通过getTask()方法获取到的任务
     *
     * 5.线程执行结束后，调用processWorkerExit()方法执行结束线程的一些清理工作
     * @param w
     */
    final void runWorker(Worker w) {
        //获取当前线程
        Thread wt = Thread.currentThread();
        //获取w的firstTask
        Runnable task = w.firstTask;
        //设置w的firstTask为null
        w.firstTask = null;
        // 释放锁，设置AQS的state为0，允许中断
        w.unlock(); // allow interrupts  允许中断
        //用于标识线程是否异常终止，finally中processWorkerExit()方法会有不同逻辑
        boolean completedAbruptly = true;
        try {
            //循环调用getTask()获取任务,不断从任务缓存队列获取任务并执行

            // 如果getTask返回null那么getTask中会将workerCount递减，
            // 如果异常了这个递减操作会在processWorkerExit中处理
            while (task != null || (task = getTask()) != null) {
                //进入循环内部，代表已经获取到可执行的任务，则对worker对象加锁，保证线程在执行任务过程中不会被中断
                //这个lock在这里是为了如果线程被中断，那么会抛出InterruptedException，而退出循环，结束线程
                w.lock();
                //判断线程是否需要中断
                if ((runStateAtLeast(ctl.get(), STOP) ||                //若线程池状态大于等于STOP，那么意味着该线程要中断
                        (Thread.interrupted() &&                        //线程被中断
                                runStateAtLeast(ctl.get(), STOP))) &&   //且是因为线程池内部状态变化而被中断
                        !wt.isInterrupted()) {                          //确保该线程未被中断
                    //发出中断请求
                    wt.interrupt();
                }
                try {
                    //任务开始执行前的hook方法
                    beforeExecute(wt, task);
                    Throwable thrown = null;
                    try {
                        //到这里正式开始执行任务
                        task.run();
                    } catch (RuntimeException x) {
                        thrown = x;
                        throw x;
                    } catch (Error x) {
                        thrown = x;
                        throw x;
                    } catch (Throwable x) {
                        thrown = x;
                        throw new Error(x);
                    } finally {
                        //任务开始执行后的hook方法
                        afterExecute(task, thrown);
                    }
                } finally {
                    //置空task，准备通过getTask()获取下一个任务
                    task = null;
                    //completedTasks递增
                    w.completedTasks++;
                    //释放掉worker持有的独占锁
                    w.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            //到这里，线程执行结束，需要执行结束线程的一些清理工作
            //线程执行结束可能有两种情况：
            //1.getTask()返回null，也就是说，这个worker的使命结束了，线程执行结束
            //2.任务执行过程中发生了异常
            //第一种情况，getTask()返回null，那么getTask()中会将workerCount递减
            //第二种情况，workerCount没有进行处理，这个递减操作会在processWorkerExit()中处理
            processWorkerExit(w, completedAbruptly);
        }
    }

    public ThreadPoolExecutor(int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                Executors.defaultThreadFactory(), defaultHandler);
    }

    public ThreadPoolExecutor(int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue,
            ThreadFactory threadFactory) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                threadFactory, defaultHandler);
    }

    public ThreadPoolExecutor(int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue,
            RejectedExecutionHandler handler) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                Executors.defaultThreadFactory(), handler);
    }

    public ThreadPoolExecutor(int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue,
            ThreadFactory threadFactory,
            RejectedExecutionHandler handler) {
        if (corePoolSize < 0 ||
                maximumPoolSize <= 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0) {
            throw new IllegalArgumentException();
        }
        if (workQueue == null || threadFactory == null || handler == null) {
            throw new NullPointerException();
        }
        this.acc = System.getSecurityManager() == null ?
                null :
                AccessController.getContext();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }

    /**
     * 如果workerCount < corePoolSize，则创建并启动一个线程来执行新提交的任务；
     * 如果workerCount >= corePoolSize，且线程池内的阻塞队列未满，则将任务添加到该阻塞队列中；
     * 如果workerCount >= corePoolSize && workerCount < maximumPoolSize，且线程池内的阻塞队列已满，则创建并启动一个线程来执行新提交的任务；
     * 如果workerCount >= maximumPoolSize，并且线程池内的阻塞队列已满, 则根据拒绝策略来处理该任务, 默认的处理方式是直接抛异常。
     */
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        int c = ctl.get();
        /******************(1)创建核心线程*************************************/
        if (workerCountOf(c) < corePoolSize) {
            /*
             * addWorker中的第二个参数表示限制添加线程的数量是根据corePoolSize来判断还是maximumPoolSize来判断；
             * 如果为true，根据corePoolSize来判断；
             * 如果为false，则根据maximumPoolSize来判断
             */
            if (addWorker(command, true)) {
                return;
            }
            /*
             * 如果添加失败，则重新获取ctl值
             */
            c = ctl.get();
        }
        //
        /******************(2)入阻塞队列*************************************/
        /*
         * 如果当前线程池是运行状态并且任务添加到队列成功
         */
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            // 再次判断线程池的运行状态，如果不是运行状态，由于之前已经把command添加到workQueue中了，
            // 这时需要移除该command
            // 执行过后通过handler使用拒绝策略对该任务进行处理，整个方法返回
            if (!isRunning(recheck) && remove(command)) {
                reject(command);
                /*
                 * 获取线程池中的有效线程数，如果数量是0，则执行addWorker方法
                 * 这里传入的参数表示：
                 * 1. 第一个参数为null，表示在线程池中创建一个线程，但不去启动；
                 * 2. 第二个参数为false，将线程池的有限线程数量的上限设置为maximumPoolSize，添加线程时根据maximumPoolSize来判断；
                 * 如果判断workerCount大于0，则直接返回，在workQueue中新增的command会在将来的某个时刻被执行。
                 *
                 * 为什么?
                 *
                 * 也就是创建一个线程，但并没有传入任务，因为任务已经被添加到workQueue中了，所以worker在执行的时候，会直接从workQueue中获取任务。
                 * 所以，在workerCountOf(recheck) == 0时执行addWorker(null, false);
                 * 也是为了保证线程池在RUNNING状态下必须要有一个线程来执行任务。
                 */
            } else if (workerCountOf(recheck) == 0) {
                addWorker(null, false);
            }
            /*
             * 如果执行到这里，有两种情况：
             * 1. 线程池已经不是RUNNING状态；
             * 2. 线程池是RUNNING状态，但workerCount >= corePoolSize并且workQueue已满。
             * 这时，再次调用addWorker方法，但第二个参数传入为false，将线程池的有限线程数量的上限设置为maximumPoolSize；
             * 如果失败则拒绝该任务
             */
        } else if (!addWorker(command, false)) {
            reject(command);
        }
    }

    public void shutdown() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            //检查shutdown权限
            checkShutdownAccess();
            //设置线程池运行状态为SHUTDOWN
            advanceRunState(SHUTDOWN);
            //中断所有空闲worker
            interruptIdleWorkers();
            //用onShutdown()钩子方法
            onShutdown(); // hook for ScheduledThreadPoolExecutor
        } finally {
            mainLock.unlock();
        }
        //尝试终止线程池
        tryTerminate();
    }

    public List<Runnable> shutdownNow() {
        List<Runnable> tasks;
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            checkShutdownAccess();
            advanceRunState(STOP);
            interruptWorkers();
            tasks = drainQueue();
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
        return tasks;
    }

    public boolean isShutdown() {
        return !isRunning(ctl.get());
    }

    public boolean isTerminating() {
        int c = ctl.get();
        return !isRunning(c) && runStateLessThan(c, TERMINATED);
    }

    public boolean isTerminated() {
        return runStateAtLeast(ctl.get(), TERMINATED);
    }

    public boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (; ; ) {
                if (runStateAtLeast(ctl.get(), TERMINATED)) {
                    return true;
                }
                if (nanos <= 0) {
                    return false;
                }
                nanos = termination.awaitNanos(nanos);
            }
        } finally {
            mainLock.unlock();
        }
    }

    protected void finalize() {
        SecurityManager sm = System.getSecurityManager();
        if (sm == null || acc == null) {
            shutdown();
        } else {
            PrivilegedAction<Void> pa = () -> {
                shutdown();
                return null;
            };
            AccessController.doPrivileged(pa, acc);
        }
    }

    public void setThreadFactory(ThreadFactory threadFactory) {
        if (threadFactory == null) {
            throw new NullPointerException();
        }
        this.threadFactory = threadFactory;
    }

    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
        if (handler == null) {
            throw new NullPointerException();
        }
        this.handler = handler;
    }

    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return handler;
    }

    public void setCorePoolSize(int corePoolSize) {
        if (corePoolSize < 0) {
            throw new IllegalArgumentException();
        }
        int delta = corePoolSize - this.corePoolSize;
        this.corePoolSize = corePoolSize;
        if (workerCountOf(ctl.get()) > corePoolSize) {
            interruptIdleWorkers();
        } else if (delta > 0) {
            int k = Math.min(delta, workQueue.size());
            while (k-- > 0 && addWorker(null, true)) {
                if (workQueue.isEmpty()) {
                    break;
                }
            }
        }
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public boolean prestartCoreThread() {
        return workerCountOf(ctl.get()) < corePoolSize &&
                addWorker(null, true);
    }

    void ensurePrestart() {
        int wc = workerCountOf(ctl.get());
        if (wc < corePoolSize) {
            addWorker(null, true);
        } else if (wc == 0) {
            addWorker(null, false);
        }
    }

    public int prestartAllCoreThreads() {
        int n = 0;
        while (addWorker(null, true)) {
            ++n;
        }
        return n;
    }

    public boolean allowsCoreThreadTimeOut() {
        return allowCoreThreadTimeOut;
    }

    public void allowCoreThreadTimeOut(boolean value) {
        if (value && keepAliveTime <= 0) {
            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
        }
        if (value != allowCoreThreadTimeOut) {
            allowCoreThreadTimeOut = value;
            if (value) {
                interruptIdleWorkers();
            }
        }
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        if (maximumPoolSize <= 0 || maximumPoolSize < corePoolSize) {
            throw new IllegalArgumentException();
        }
        this.maximumPoolSize = maximumPoolSize;
        if (workerCountOf(ctl.get()) > maximumPoolSize) {
            interruptIdleWorkers();
        }
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setKeepAliveTime(long time, TimeUnit unit) {
        if (time < 0) {
            throw new IllegalArgumentException();
        }
        if (time == 0 && allowsCoreThreadTimeOut()) {
            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
        }
        long keepAliveTime = unit.toNanos(time);
        long delta = keepAliveTime - this.keepAliveTime;
        this.keepAliveTime = keepAliveTime;
        if (delta < 0) {
            interruptIdleWorkers();
        }
    }

    public long getKeepAliveTime(TimeUnit unit) {
        return unit.convert(keepAliveTime, TimeUnit.NANOSECONDS);
    }

    public BlockingQueue<Runnable> getQueue() {
        return workQueue;
    }

    public boolean remove(Runnable task) {
        boolean removed = workQueue.remove(task);
        tryTerminate(); // In case SHUTDOWN and now empty
        return removed;
    }

    public void purge() {
        final BlockingQueue<Runnable> q = workQueue;
        try {
            Iterator<Runnable> it = q.iterator();
            while (it.hasNext()) {
                Runnable r = it.next();
                if (r instanceof Future<?> && ((Future<?>) r).isCancelled()) {
                    it.remove();
                }
            }
        } catch (ConcurrentModificationException fallThrough) {

            for (Object r : q.toArray()) {
                if (r instanceof Future<?> && ((Future<?>) r).isCancelled()) {
                    q.remove(r);
                }
            }
        }

        tryTerminate(); // In case SHUTDOWN and now empty
    }

    public int getPoolSize() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            // Remove rare and surprising possibility of
            // isTerminated() && getPoolSize() > 0
            return runStateAtLeast(ctl.get(), TIDYING) ? 0
                    : workers.size();
        } finally {
            mainLock.unlock();
        }
    }

    public int getActiveCount() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            int n = 0;
            for (Worker w : workers) {
                if (w.isLocked()) {
                    ++n;
                }
            }
            return n;
        } finally {
            mainLock.unlock();
        }
    }

    public int getLargestPoolSize() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            return largestPoolSize;
        } finally {
            mainLock.unlock();
        }
    }

    public long getTaskCount() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            long n = completedTaskCount;
            for (Worker w : workers) {
                n += w.completedTasks;
                if (w.isLocked()) {
                    ++n;
                }
            }
            return n + workQueue.size();
        } finally {
            mainLock.unlock();
        }
    }

    public long getCompletedTaskCount() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            long n = completedTaskCount;
            for (Worker w : workers) {
                n += w.completedTasks;
            }
            return n;
        } finally {
            mainLock.unlock();
        }
    }

    public String toString() {
        long ncompleted;
        int nworkers, nactive;
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            ncompleted = completedTaskCount;
            nactive = 0;
            nworkers = workers.size();
            for (Worker w : workers) {
                ncompleted += w.completedTasks;
                if (w.isLocked()) {
                    ++nactive;
                }
            }
        } finally {
            mainLock.unlock();
        }
        int c = ctl.get();
        String rs = (runStateLessThan(c, SHUTDOWN) ? "Running" :
                (runStateAtLeast(c, TERMINATED) ? "Terminated" :
                        "Shutting down"));
        return super.toString() +
                "[" + rs +
                ", pool size = " + nworkers +
                ", active threads = " + nactive +
                ", queued tasks = " + workQueue.size() +
                ", completed tasks = " + ncompleted +
                "]";
    }

    protected void beforeExecute(Thread t, Runnable r) { }

    protected void afterExecute(Runnable r, Throwable t) { }

    protected void terminated() { }

    /**
     * 在调用execute的线程里面执行此command，会阻塞入口
     * <p>
     * 用户自定义拒绝策略（最常用）
     */
    public static class CallerRunsPolicy implements RejectedExecutionHandler {

        public CallerRunsPolicy() { }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                r.run();
            }
        }
    }

    /**
     * 为Java线程池默认的阻塞策略，不执行此任务，而且直接抛出一个运行时异常，
     * 切记ThreadPoolExecutor.execute需要try catch，否则程序会直接退出。
     */
    public static class AbortPolicy implements RejectedExecutionHandler {

        public AbortPolicy() { }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            throw new RejectedExecutionException("Task " + r.toString() + " rejected from " + e.toString());
        }
    }

    /**
     * 直接抛弃，任务不执行，空方法
     */
    public static class DiscardPolicy implements RejectedExecutionHandler {

        public DiscardPolicy() { }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        }
    }

    /**
     * 从队列里面抛弃head的一个任务，并再次execute 此task。
     */
    public static class DiscardOldestPolicy implements RejectedExecutionHandler {

        public DiscardOldestPolicy() { }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                e.getQueue().poll();
                e.execute(r);
            }
        }
    }
}

