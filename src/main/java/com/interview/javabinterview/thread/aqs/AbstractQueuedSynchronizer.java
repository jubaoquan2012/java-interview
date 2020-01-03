package com.interview.javabinterview.thread.aqs;

import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;


public abstract class AbstractQueuedSynchronizer
        extends AbstractOwnableSynchronizer
        implements java.io.Serializable {

    private static final long serialVersionUID = 7373984972572414691L;

    protected AbstractQueuedSynchronizer() {
    }

    static final class Node {

        /**
         * Exclusive（独占，只有一个线程能执行，如ReentrantLock）
         */
        static final Node SHARED = new Node();

        /**
         * （共享，多个线程可同时执行，如Semaphore/CountDownLatch）
         */
        static final Node EXCLUSIVE = null;
        /**
         * 线程被取消了
         */
        static final int CANCELLED = 1;
        /**
         * 线程需要被唤醒
         */
        static final int SIGNAL = -1;
        /**
         * 线程在条件队列里面等待
         */
        static final int CONDITION = -2;
        /**
         * 代表后续结点会传播唤醒的操作，共享模式下起作用
         */
        static final int PROPAGATE = -3;

        volatile int waitStatus;

        /**
         * 记录当前节点的前驱节点
         */
        volatile Node prev;

        /**
         * 记录当前节点的后继节点
         */
        volatile Node next;

        /**
         * 进去AQS队列的线程
         */
        volatile Thread thread;

        Node nextWaiter;

        final boolean isShared() {
            return nextWaiter == SHARED;
        }

        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }

        Node() {
        }

        Node(Thread thread, Node mode) {
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread, int waitStatus) {
            this.waitStatus = waitStatus;
            this.thread = thread;
        }
    }

    private transient volatile Node head;

    private transient volatile Node tail;

    /**
     * volatile 修饰,保证了可见性
     * ReentrantLock 表示当前线程获取锁的可重入次数
     * ReentrantReadWriteLock:
     * state高16位表示读状态,也就是获取读锁的次数
     * state低26位表示写状态,也就是获取到写锁的线程的可重入次数
     * semaphore: state表示当前可用信号的个数
     * CountDownLatch: state表示计数器当前的值
     */
    private volatile int state;

    protected final int getState() {
        return state;
    }

    protected final void setState(int newState) {
        state = newState;
    }

    protected final boolean compareAndSetState(int expect, int update) {
        // See below for intrinsics setup to support this
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    static final long spinForTimeoutThreshold = 1000L;

    /**
     * 将新的节点插入到等待队列的尾部。如果等待队列不存在，一定要对其初始化
     */

    /**
     * unparkSuccessor 方法中for循环从tail 开始而不是header 开始的原因:
     *
     *① 处将新结点 node 的 prev 引用指向当前的 t, 即 tail 结点. 然而, 由于 ①, ② 这两行代码的合在一起并非原子性的,
     * 所以很有可能在设置 tail 时存在着竞争, 也即 tail 被其它线程更新过了. 所以要自旋操作, 即在死循环中操作, 直到成功为止.
     * 自旋地 CAS volatile 变量是很经典的用法. 如果设置成功了, 那么 从 node.prev 执行完毕到正在用 CAS 设置 tail 时,
     * tail 变量是没有被修改的, 所以如果 CAS成功, 那么 node.prev = t 一定是指向上一个 tail 的. 同样的, ②, ③ 合在一起也并非原子操作,
     * 更重要的是, next field 的设置发生在 CAS 操作之后, 所以可能会存在 tail 已经更新, 但是 last tail 的 next field 还未设置完毕,
     * 即它的 lastTail.next 为 null 这种情况. 因此如果此时访问该结点的 next 引用可能就会得到它在队尾, 不存在后继结点的"错觉".
     * 而我们总是能够通过从 tail 开始反向查找, 借助可靠的 prev 引用来定位到指定的结点. 简单总结一下, prev 引用的设置发生在 CAS之前,
     * 因此如果 CAS 设置 tail 成功, 那么 prev 一定是正确地指向 last tail, 而 next 引用的设置发生在其后, 因而会存在一个 tail 更新成功,
     * 但是 last  tail 的 next 引用还未设置的尴尬时期. 所以我们说 prev 是可靠的, 而 next 有时会为 null, 但并不一定真的就没有后继结点.
     */
    private Node enq(final Node node) {
        for (; ; ) {
            Node t = tail;
            if (t == null) { // Must initialize
                // 队列为空情况下，CAS操作将头节点进行更新，将头节点设置为空节点(哨兵节点)
                if (compareAndSetHead(new Node()))//CAS 设置空节 Node()点为 head
                    tail = head; //tail =(哨兵 new Node()) = head
            } else {
                // 尾节点存在的情况下，代表AQS等待队列已经初始化完成。
                // 与addWaiter一样，先将新增节点的前驱节点链接到尾节点
                node.prev = t;  //第一次初始化的时候: head = tail = new Node();    // ①
                if (compareAndSetTail(t, node)) {                                  // ②
                    // 当更新尾节点成功后，就将原尾节点的后继节点连接为新增的节点  // ③
                    t.next = node;
                    return t;
                }
            }
        }
    }

    private Node addWaiter(Node mode) {
        // 创建一个新的节点,设置节点中nextWaiter为: EXCLUSIVE 或者 SHARED
        Node node = new Node(Thread.currentThread(), mode);
        Node pred = tail;
        // 当等待队列尾部不为空时，有在等待的线程
        if (pred != null) {
            // 将新增的节点的前驱节点链接到尾部节点。
            node.prev = pred;
            // // 采用CAS的方式进行数据的更新，更新成功后，则将原尾节点的后继节点链接到新增的节点
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        // 尾结点为null,说明队列为空
        enq(node);
        return node;
    }

    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }

    /**
     *  唤醒后继结点
     * @param node 当前节点
     */
    private void unparkSuccessor(Node node) {
        int ws = node.waitStatus;
        // 如果当前节点的状态小于0，将状态更新为0，重置一下
        if (ws < 0) compareAndSetWaitStatus(node, ws, 0);
        Node s = node.next;
        // 后继节点不存在或者后继节点的waitStatus大于0（也就是被取消的状态）
        if (s == null || s.waitStatus > 0) {
            // 置后继节点为null，
            s = null;
            // 若当前节点发生了取消操作，重新从尾部开始遍历，找到没有标记为取消的节点进行阻塞操作
            //循环找到最前面第一个不为null,且
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }
        if (s != null)
            LockSupport.unpark(s.thread);
    }

    /**
     *   //从头节点开始， 判断头节点后继的状态，来确定后继需不需要唤醒。
     */
    private void doReleaseShared() {
        for (; ; ) {
            Node h = head;
            // 只需要处理头节点和尾节点都存在，且队列内的节点总数超过1个的情况
            if (h != null && h != tail) {
                int ws = h.waitStatus;
                // 两种模式下都需要SIGNAL信号来判断是否唤醒后继节点
                if (ws == Node.SIGNAL) {
                    // 如果CAS操作失败了就继续循环处理
                    if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0)){
                        continue;            // loop to recheck cases
                    }
                    // CAS操作成功后，就将后继节点解除阻塞
                    unparkSuccessor(h);
                } else if (ws == 0 && !compareAndSetWaitStatus(h, 0, Node.PROPAGATE)){
                    continue;                // loop on failed CAS
                }
                // 当状态码是PROPAGATE的时候，就可以结束循环了
            }
            // 在循环过程中，为了防止在上述操作过程中新添加了节点的情况，
            // 通过检查头节点是否改变了，如果改变了就继续循环
            if (h == head)                   // loop if head changed
                break;
        }
    }

    /**
     * 设置头节点状态，并通过propagate判断是否可以允许acquire
     */
    private void setHeadAndPropagate(Node node, int propagate) {
        Node h = head; //备份当前 head
        setHead(node);
        // propagate也就是state的更新值大于0，代表可以继续acquire

        /**
         *这里为什么要判断状态为小于0的情况，直接使用PROPAGATE状态不可以吗？
         * 这个是因为PROPAGATE状态是会被转换为SIGNAL的，这个在shouldParkAfterFailedAcquire方法中处理的
         */
        if (propagate > 0 || h == null || h.waitStatus < 0 ||
                (h = head) == null || h.waitStatus < 0) {
            Node s = node.next;
            // 判断后继节点是否存在，如果存在是否是共享模式的节点
            // 然后进行共享模式的释放
            // 后继节点是共享模式或者s == null（不知道什么情况）
            // 如果后继是独占模式，那么即使剩下的许可大于0也不会继续往后传递唤醒操作
            // 即使后面有结点是共享模式。
            if (s == null || s.isShared())
                doReleaseShared();
        }
    }

    /**
     * 1.取消的节点是队尾节点tail。那么直接将pred的next和当前节点的prev全部置null。重新设置一下tail节点。
     * 2.取消的节点是队列中的一个节点，并且不是head头节点的直接后继节点。那么也很容易理解，
     * 就是将pred的next和当前节点的next进行一个链接。那么有一个疑问，代码中只将next节点链接了，
     * prev的节点如何连接。这里的prev是由其它线程做的，由于当前节点已经是CANCEL状态，其他线程在进行竞争时，
     * 都会遍历前驱节点，重新链接前驱节点完成prev的链接。
     * 3.取消的节点是队列头节点head的直接后继节点。那么如果取消的节点后继节点是存在的，可以直接唤醒后继节点。
     *
     * @param node
     */
    private void cancelAcquire(Node node) {
        if (node == null)
            return;
        node.thread = null;
        Node pred = node.prev;
        while (pred.waitStatus > 0)
            node.prev = pred = pred.prev;
        Node predNext = pred.next;
        node.waitStatus = Node.CANCELLED;
        if (node == tail && compareAndSetTail(node, pred)) {
            compareAndSetNext(pred, predNext, null);
        } else {
            int ws;
            if (pred != head &&
                    ((ws = pred.waitStatus) == Node.SIGNAL ||
                            (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) &&
                    pred.thread != null) {
                Node next = node.next;
                if (next != null && next.waitStatus <= 0)
                    compareAndSetNext(pred, predNext, next);
            } else {
                unparkSuccessor(node);
            }
            node.next = node; // help GC
        }
    }

    /**
     * https://www.jianshu.com/p/63588ebea397
     * <p>
     * 首先，这个方法的总目的: 要让pre结点的状态为SIGNAL，
     * 这个状态的意思是:pre结点的下一个要被block或者已经是blocked了，
     * pre结点（自己）在他自己释放锁的时候必须要做unpark操作来叫醒park的node（后面那个）。
     * <p>
     * <p>
     * 这个函数是整个循环中处理信号状态的唯一方法。信号是什么？也就是我们在Node章节提到的waitStatus状态。
     * waitStatus状态初始化的值是0。如代码中描述的，waitStatus只有前驱节点是SIGNAL状态才会将当前的线程阻塞住。
     * 其他情况下，waitStatus为非正数时，就会将前驱节点的状态更新为SIGNAL。而状态为正数时，代表节点被取消了，
     * 就会不断向前寻找，直到找到未被取消的节点。取消的节点通通会被垃圾回收机制处理掉。
     */

    /**
     * 为什么需要SIGNAL信号才阻塞线程呢？
     * 其实只是为了在线程park阻塞和解除阻塞的次数不那么频繁，减少了不必要的线程阻塞切换时间。
     * SIGNAL设置后，只有确定SIGNAL信号的线程才会阻塞。
     */
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        //1. 首先获取当前节点的pre结点的状态；
        int ws = pred.waitStatus;
        // 只有SIGNAL状态才能阻塞线程
        if (ws == Node.SIGNAL)
            return true;
        //pre结点的状态大于0，也就是CANCELLED，那么就要改pre指针，跳过这些cancelled的node，
        // 直到找到一个不是cancelled的node作为新的pre结点。
        if (ws > 0) {
            do {
                // 向队列的前驱节点找,一直到找到一个状态码小于0的,也就是非CANCEL状态的
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            // 如果前驱节点的状态是小于0的，CAS方式将前驱节点的状态更新为SIGNAL。同时不对线程阻塞
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }

    static void selfInterrupt() {
        Thread.currentThread().interrupt();
    }

    /**
     * LockSupport.park对线程进行了阻塞，返回线程是否被中断的状态。
     * LockSupport的细节不做解释，简单来说，它主要是用来解决Thread.suspend这种方法会产生死锁的问题，
     * 同时能够对线程进行阻塞，核心是由native方法进行处理
     *
     * @return
     */
    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();//注意哦，这个方法如果刚刚是interrupt的，会返回true，然后清除当前线程interrupt的状态
    }

    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (; ; ) {
                //获取当前节点的先驱节点
                final Node p = node.predecessor();
                /**
                 * 如果当前节点的前一个节点是head 节点(说明当前节点已经是头节点了) ,并且获取资源成功
                 * 这个if告诉我们，只有是你的node排队排到第二个了，就下一个就队头了，
                 * 才能再次看能不能获得锁，就用我们重写的tryAcquire来获取锁
                 */
                // 该节点的前驱节点是头节点，就尝试去获取一下许可
                // 当获取许可成功后，就可以将头节点设置为当前的节点
                // 并将后继节点置空，重置状态
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                // 当获取许可失败后，会判断需不需要对线程进行阻塞
                if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            // 如果执行过程中出现了了错误，或者中断后，就会对数据进行处理，完成销毁的过程。
            if (failed)
                cancelAcquire(node);
        }
    }

    private void doAcquireInterruptibly(int arg)
            throws InterruptedException {
        final Node node = addWaiter(Node.EXCLUSIVE);
        boolean failed = true;
        try {
            for (; ; ) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    private boolean doAcquireNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (nanosTimeout <= 0L)
            return false;
        final long deadline = System.nanoTime() + nanosTimeout;
        final Node node = addWaiter(Node.EXCLUSIVE);
        boolean failed = true;
        try {
            for (; ; ) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return true;
                }
                nanosTimeout = deadline - System.nanoTime();
                if (nanosTimeout <= 0L)
                    return false;
                if (shouldParkAfterFailedAcquire(p, node) &&
                        nanosTimeout > spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if (Thread.interrupted())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    private void doAcquireShared(int arg) {
        //此处与独占式不同点:addWaiter(Node.EXCLUSIVE)
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (; ; ) {
                final Node p = node.predecessor();
                if (p == head) {
                    //tryAcquire和tryAcquireShared的返回值不同，因此会多出一个判断过程
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        if (interrupted)
                            selfInterrupt();
                        failed = false;
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    private void doAcquireSharedInterruptibly(int arg)
            throws InterruptedException {
        //加入等待队列
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        //进入 CAS 循环
        try {
            for (; ; ) {
                //当一个节点(关联一个线程)进入等待队列后， 获取此节点的 prev 节点
                final Node p = node.predecessor();
                // 如果获取到的 prev 是 head，也就是队列中第一个等待线程
                if (p == head) {
                    // 再次尝试申请 反应到 CountDownLatch 就是查看是否还有线程需要等待(state是否为0)
                    int r = tryAcquireShared(arg);// return (getState() == 0) ? 1 : -1;
                    //如果 r >=0 说明 没有线程需要等待了 state==0
                    if (r >= 0) {
                        //尝试将第一个线程关联的节点设置为 head
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        failed = false;
                        return;
                    }
                }
                //经过自旋tryAcquireShared后，state还不为0，就会到这里，第一次的时候，waitStatus是0，
                //那么node的waitStatus就会被置为SIGNAL，第二次再走到这里，就会用LockSupport的park方法把当前线程阻塞住
                if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    private boolean doAcquireSharedNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (nanosTimeout <= 0L)
            return false;
        final long deadline = System.nanoTime() + nanosTimeout;
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            for (; ; ) {
                final Node p = node.predecessor();
                if (p == head) {
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        failed = false;
                        return true;
                    }
                }
                nanosTimeout = deadline - System.nanoTime();
                if (nanosTimeout <= 0L)
                    return false;
                if (shouldParkAfterFailedAcquire(p, node) &&
                        nanosTimeout > spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if (Thread.interrupted())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }


    /**
     * 独占方式。尝试获取资源，成功则返回true，失败则返回false。
     */
    protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * 独占方式。尝试释放资源，成功则返回true，失败则返回false。
     */
    protected boolean tryRelease(int arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * 共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
     */
    protected int tryAcquireShared(int arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * 共享方式。尝试释放资源，如果释放后允许唤醒后续等待结点返回true，否则返回false。
     */
    protected boolean tryReleaseShared(int arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * 该线程是否正在独占资源。只有用到condition才需要去实现它。
     */
    protected boolean isHeldExclusively() {
        throw new UnsupportedOperationException();
    }

    /**
     * acquire是一种以独占方式获取资源，如果获取到资源，线程直接返回，否则进入等待队列，直到获取到资源为止，且整个过程忽略中断的影响。
     * 该方法是独占模式下线程获取共享资源的顶层入口。获取到资源后，线程就可以去执行其临界区代码了
     * <p>
     * 伪代码解释:
     * acquire {
     * if (!tryAcquire(arg)) {
     * node = 创建队列并且新入队节点;                  对应:addWaiter()
     * pred = 节点的有效前驱节点;
     * while (pred 不是头节点 || !tryAcquire(arg)) {  对应:acquireQueued()
     * if (pred的状态位是Signal信号)              对应:shouldParkAfterFailedAcquire() 中的if (ws == Node.SIGNAL) return true;
     * park();                               对应:parkAndCheckInterrupt ()
     * else
     * CAS操作设置pred的Signal信号;           对应: shouldParkAfterFailedAcquire()中的compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
     * pred = node节点的有效前驱节点;
     * }
     * head = node;
     * }
     * }
     */
    public final void acquire(int arg) {
        /**
         * https://www.cnblogs.com/wangshen31/p/10478129.html
         * https://www.jianshu.com/p/d2e5965631dc
         * tryAcquire（）尝试获取资源,
         *  成功直接返回
         *  失败:
         *      1.执行 addWaiter()将该线程加入等待队列的尾部，并标记为独占模式；
         *      2.acquireQueued()使线程在等待队列中获取资源，一直获取到资源后才返回。如果在整个等待过程中被中断过，则返回true，否则返回false。
         * 4.如果线程在等待过程中被中断过，它是不响应的。只是获取资源后才再进行自我中断selfInterrupt()，将中断补上。
         */
        if (!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
    }

    public final void acquireInterruptibly(int arg)
            throws InterruptedException {
        //如果当前线程被中断,则直接抛出异常
        if (Thread.interrupted())
            throw new InterruptedException();
        //尝试获取资源
        if (!tryAcquire(arg))
            //调用AQS 可被中断的方法
            doAcquireInterruptibly(arg);
    }


    public final boolean tryAcquireNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        return tryAcquire(arg) ||
                doAcquireNanos(arg, nanosTimeout);
    }

    /**
     * 伪代码逻辑:
     * release {
     * if (tryRelease(arg) && 头节点的状态是Signal) {
     * 将头节点的状态设置为不是Signal;
     * 如果头节点的后继结点存在，则将其唤醒。
     * }
     * }
     */
    public final boolean release(int arg) {
        if (tryRelease(arg)) {
            Node h = head;
            /**
             * 若头节点存在，且头节点的waitStatus状态是非0，也就说明AQS不是初始化阶段
             *
             * waitStatus状态是0，代表这个节点的状态还未更新，因此AQS没有将后继节点进行阻塞，
             * 这个时候就不能去做unpark的操作。
             */
            if (h != null && h.waitStatus != 0)
                //将后继节点的线程唤醒
                unparkSuccessor(h);
            return true;
        }
        return false;
    }

    public final void acquireShared(int arg) {
        if (tryAcquireShared(arg) < 0)
            doAcquireShared(arg);
    }

    public final void acquireSharedInterruptibly(int arg)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (tryAcquireShared(arg) < 0)//尝试获取资源,获取失败执行 doAcquireSharedInterruptibly(arg);
            doAcquireSharedInterruptibly(arg);
    }


    public final boolean tryAcquireSharedNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        return tryAcquireShared(arg) >= 0 ||
                doAcquireSharedNanos(arg, nanosTimeout);
    }

    public final boolean releaseShared(int arg) {
        if (tryReleaseShared(arg)) {
            doReleaseShared();
            return true;
        }
        return false;
    }

    public final boolean hasQueuedThreads() {
        return head != tail;
    }

    public final boolean hasContended() {
        return head != null;
    }

    public final Thread getFirstQueuedThread() {
        // handle only fast path, else relay
        return (head == tail) ? null : fullGetFirstQueuedThread();
    }

    private Thread fullGetFirstQueuedThread() {
        Node h, s;
        Thread st;
        if (((h = head) != null && (s = h.next) != null &&
                s.prev == head && (st = s.thread) != null) ||
                ((h = head) != null && (s = h.next) != null &&
                        s.prev == head && (st = s.thread) != null))
            return st;

        Node t = tail;
        Thread firstThread = null;
        while (t != null && t != head) {
            Thread tt = t.thread;
            if (tt != null)
                firstThread = tt;
            t = t.prev;
        }
        return firstThread;
    }

    public final boolean isQueued(Thread thread) {
        if (thread == null)
            throw new NullPointerException();
        for (Node p = tail; p != null; p = p.prev)
            if (p.thread == thread)
                return true;
        return false;
    }


    final boolean apparentlyFirstQueuedIsExclusive() {
        Node h, s;
        return (h = head) != null &&
                (s = h.next) != null &&
                !s.isShared() &&
                s.thread != null;
    }


    public final boolean hasQueuedPredecessors() {
        Node t = tail; // Read fields in reverse initialization order
        Node h = head;
        Node s;
        /**
         * 三种情况: 直接返回 false:
         * 1.h == t :当前队列为空,直接返回 false
         * 2. h != t && s == null :
         *      说明有一个元素将要作为AQS 的第一个节点入队列(就是addWaiter()方法中: enq()中:
         *        首先创建一个哨兵节点,然后将第一个元素,插入到哨兵节点后面. )
         * 3. h != t && s != null 和 s.thread != Thread.currentThread() :
         *      说明队列里的第一个元素表示当前线程
         */
        return h != t && ((s = h.next) == null || s.thread != Thread.currentThread());
    }


    public final int getQueueLength() {
        int n = 0;
        for (Node p = tail; p != null; p = p.prev) {
            if (p.thread != null)
                ++n;
        }
        return n;
    }


    public final Collection<Thread> getQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            Thread t = p.thread;
            if (t != null)
                list.add(t);
        }
        return list;
    }


    public final Collection<Thread> getExclusiveQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            if (!p.isShared()) {
                Thread t = p.thread;
                if (t != null)
                    list.add(t);
            }
        }
        return list;
    }

    public final Collection<Thread> getSharedQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            if (p.isShared()) {
                Thread t = p.thread;
                if (t != null)
                    list.add(t);
            }
        }
        return list;
    }

    public String toString() {
        int s = getState();
        String q = hasQueuedThreads() ? "non" : "";
        return super.toString() +
                "[State = " + s + ", " + q + "empty queue]";
    }

    final boolean isOnSyncQueue(Node node) {
        if (node.waitStatus == Node.CONDITION || node.prev == null)
            return false;
        if (node.next != null) // If has successor, it must be on queue
            return true;

        return findNodeFromTail(node);
    }


    private boolean findNodeFromTail(Node node) {
        Node t = tail;
        for (; ; ) {
            if (t == node)
                return true;
            if (t == null)
                return false;
            t = t.prev;
        }
    }


    final boolean transferForSignal(Node node) {
        if (!compareAndSetWaitStatus(node, Node.CONDITION, 0)) return false;
        Node p = enq(node);
        int ws = p.waitStatus;
        if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL))
            LockSupport.unpark(node.thread);
        return true;
    }


    final boolean transferAfterCancelledWait(Node node) {
        if (compareAndSetWaitStatus(node, Node.CONDITION, 0)) {
            enq(node);
            return true;
        }
        while (!isOnSyncQueue(node))
            Thread.yield();
        return false;
    }


    final int fullyRelease(Node node) {
        boolean failed = true;
        try {
            int savedState = getState();
            if (release(savedState)) {
                failed = false;
                return savedState;
            } else {
                throw new IllegalMonitorStateException();
            }
        } finally {
            if (failed)
                node.waitStatus = Node.CANCELLED;
        }
    }

    public final boolean owns(ConditionObject condition) {
        return condition.isOwnedBy(this);
    }

    public final boolean hasWaiters(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.hasWaiters();
    }

    public final int getWaitQueueLength(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitQueueLength();
    }

    public final Collection<Thread> getWaitingThreads(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitingThreads();
    }


    public class ConditionObject implements Condition, java.io.Serializable {
        private static final long serialVersionUID = 1173984872572414699L;
        /**
         * First node of condition queue.
         */
        private transient Node firstWaiter;
        /**
         * Last node of condition queue.
         */
        private transient Node lastWaiter;

        public ConditionObject() {
        }

        private Node addConditionWaiter() {
            Node t = lastWaiter;
            // If lastWaiter is cancelled, clean out.
            //根据当前线程创建一个类型为Node.CONDITION 的节点, 然后通过
            if (t != null && t.waitStatus != Node.CONDITION) {
                unlinkCancelledWaiters();
                t = lastWaiter;
            }
            Node node = new Node(Thread.currentThread(), Node.CONDITION);
            if (t == null)
                firstWaiter = node;
            else
                t.nextWaiter = node;
            lastWaiter = node;
            return node;
        }

        private void doSignal(Node first) {
            do {
                if ((firstWaiter = first.nextWaiter) == null)
                    lastWaiter = null;
                first.nextWaiter = null;
            } while (!transferForSignal(first) && (first = firstWaiter) != null);
        }

        private void doSignalAll(Node first) {
            lastWaiter = firstWaiter = null;
            do {
                Node next = first.nextWaiter;
                first.nextWaiter = null;
                transferForSignal(first);
                first = next;
            } while (first != null);
        }

        private void unlinkCancelledWaiters() {
            Node t = firstWaiter;
            Node trail = null;
            while (t != null) {
                Node next = t.nextWaiter;
                if (t.waitStatus != Node.CONDITION) {
                    t.nextWaiter = null;
                    if (trail == null)
                        firstWaiter = next;
                    else
                        trail.nextWaiter = next;
                    if (next == null)
                        lastWaiter = trail;
                } else
                    trail = t;
                t = next;
            }
        }

        /**
         * 当一个线程调用条件变量的signal 方法时(必须先调用锁的lock()方法获取锁),
         * 在内部会把条件队里面队头(firstWaiter) 的一个线程节点从条件队列里面移除并放入AQS的阻塞队列里面,
         * 然后激活这个线程
         */
        public final void signal() {
            if (!isHeldExclusively()) throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignal(first);
        }

        public final void signalAll() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignalAll(first);
        }

        public final void awaitUninterruptibly() {
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            boolean interrupted = false;
            while (!isOnSyncQueue(node)) {
                LockSupport.park(this);
                if (Thread.interrupted())
                    interrupted = true;
            }
            if (acquireQueued(node, savedState) || interrupted)
                selfInterrupt();
        }

        /**
         * Mode meaning to reinterrupt on exit from wait
         */
        private static final int REINTERRUPT = 1;
        /**
         * Mode meaning to throw InterruptedException on exit from wait
         */
        private static final int THROW_IE = -1;

        private int checkInterruptWhileWaiting(Node node) {
            return Thread.interrupted() ?
                    (transferAfterCancelledWait(node) ? THROW_IE : REINTERRUPT) :
                    0;
        }

        private void reportInterruptAfterWait(int interruptMode)
                throws InterruptedException {
            if (interruptMode == THROW_IE)
                throw new InterruptedException();
            else if (interruptMode == REINTERRUPT)
                selfInterrupt();
        }

        public final void await() throws InterruptedException {
            if (Thread.interrupted()) throw new InterruptedException();
            //创建新的node节点,并插入到条件队列末尾
            Node node = addConditionWaiter();
            //释放当前线程获取到的锁
            int savedState = fullyRelease(node);
            int interruptMode = 0;
            //调用park 方法阻塞挂起当前线程
            while (!isOnSyncQueue(node)) {
                LockSupport.park(this);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null) // clean up if cancelled
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
        }

        public final long awaitNanos(long nanosTimeout)
                throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            final long deadline = System.nanoTime() + nanosTimeout;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (nanosTimeout <= 0L) {
                    transferAfterCancelledWait(node);
                    break;
                }
                if (nanosTimeout >= spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                nanosTimeout = deadline - System.nanoTime();
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return deadline - System.nanoTime();
        }

        public final boolean awaitUntil(Date deadline)
                throws InterruptedException {
            long abstime = deadline.getTime();
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (System.currentTimeMillis() > abstime) {
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                LockSupport.parkUntil(this, abstime);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }

        public final boolean await(long time, TimeUnit unit)
                throws InterruptedException {
            long nanosTimeout = unit.toNanos(time);
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            final long deadline = System.nanoTime() + nanosTimeout;
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (nanosTimeout <= 0L) {
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                if (nanosTimeout >= spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                nanosTimeout = deadline - System.nanoTime();
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }

        final boolean isOwnedBy(AbstractQueuedSynchronizer sync) {
            return sync == AbstractQueuedSynchronizer.this;
        }

        protected final boolean hasWaiters() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION)
                    return true;
            }
            return false;
        }

        protected final int getWaitQueueLength() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            int n = 0;
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION)
                    ++n;
            }
            return n;
        }

        protected final Collection<Thread> getWaitingThreads() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            ArrayList<Thread> list = new ArrayList<Thread>();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION) {
                    Thread t = w.thread;
                    if (t != null)
                        list.add(t);
                }
            }
            return list;
        }
    }

    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizer.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizer.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizer.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                    (Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                    (Node.class.getDeclaredField("next"));

        } catch (Exception ex) {
            throw new Error(ex);
        }
    }


    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }

    private static final boolean compareAndSetWaitStatus(Node node,
                                                         int expect,
                                                         int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset,
                expect, update);
    }

    private static final boolean compareAndSetNext(Node node,
                                                   Node expect,
                                                   Node update) {
        return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
    }
}
