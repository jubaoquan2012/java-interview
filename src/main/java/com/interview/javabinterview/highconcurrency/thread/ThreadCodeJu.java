package com.interview.javabinterview.highconcurrency.thread;

import java.util.concurrent.*;

/**
 * @author Ju Baoquan
 * Created at  2020/5/18
 */
public class ThreadCodeJu {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        method_1();// 创建线程的方式
        method_2();// 线程的状态
        method_3();// Thread、Callable、Runnable三者关系
        method_4();// method_4
        method_5();// 线程常用的方法:sleep、join、wait、notify等
    }

    /**
     * 创建线程的四种方式:
     * <p>
     * 1.继承Thread
     * 2.实现Runnable
     * 3.实现Callable
     * 4.线程池创建
     */
    private static void method_1() throws ExecutionException, InterruptedException {
        /**
         * 继承 Thread 启动方式
         */
        CreateByThread createByThread = new CreateByThread();
        createByThread.start();

        /**
         * 实现 Runnable 启动方式
         */
        CreateByRunnable createByRunnable = new CreateByRunnable();
        Thread thread = new Thread(createByRunnable);
        thread.start();

        /**
         * 实现 Callable<V> 启动方法 1
         */
        CreateByCallable createByCallable = new CreateByCallable();
        FutureTask<String> futureTask = new FutureTask<>(createByCallable);
        new Thread(futureTask).start();
        String str1 = futureTask.get();

        /**
         * 实现 Callable<V> 启动方法 2
         */
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(createByCallable);
        String str2 = future.get();
    }

    /** 线程的状态 */
    private static void method_2() {
        /**
         * 3.Thread的状态:
         *      NEW:    初始化状态:
         *        new Thread()之后
         *      RUNNABLE: 就绪状态
         *        Thread.start
         *      BLOCKED: 阻塞状态
         *          等待进去 synchronized方法或代码块
         *      WAITING:等待
         *          Object.wait()、Object.join() 、LockSupport.park()之后
         *      TIMED_WAITING:超时等待
         *         Object.sleep(long)、Object.wait(long)、Object.join(long) 、LockSupport.park(long)之后
         *      TERMINATED: 终止状态
         *          线程执行完毕
         *
         *     注释: BLOCKED WAITING TIMED_WAITING三种状态 解除阻塞或等待后进入到 RUNNABLE 状态
         */
    }

    /**
     * Thread、Callable、Runnable三者关系
     */
    private static void method_3() {
        /**
         *  Runnable;
         *      是线程的接口定义类
         *  Thread
         *      实现Runnable接口重写了run方法
         *  Callable
         *      1.是有返回值的.
         *      2.通过FutureTask进行包装, FutureTask implements RunnableFuture  extends Runnable, Future
         *      3.然后再FutureTask.run(c.call)方法中调用call方法
         */
    }

    /**
     * FutureTask理解
     */
    private static void method_4() {
        /**
         * FutureTask:
         * 1).实现 RunnableFuture extends Runnable, Future
         * 2).所以具有线程运行和 结果保存的能力
         * 3).在FutureTask.run方法中. set(result) 保存结果, 然后park线程
         * 4).任务提交后，会调用 get方法获取结果，这个get方法是阻塞的。
         * 5).中间的阻塞是通过一个状态值进行控制. 如果没有完成就调用awaitDone()方法进行阻塞.
         */
    }

    /**
     * Thread常用API
     */
    private static void method_5() throws InterruptedException {
        Thread thread = new Thread();

        /**
         * 当前线程调用前一个线程的join方法,
         * 当前线程需要等待 前一个线程终止之后 才从 前一个线程join()返回。然后执行当前join以下的逻辑:
         * 简单来说，就是线程没有执行完之前，会一直阻塞在join方法处。
         */
        thread.join();
        thread.join(1000);

        /**
         * 让出当前线程CPU使用权,使得当前线程出入RUNNABLE状态:
         * 下一轮调度依然有可能调度到当前线程
         *
         */
        Thread.yield();

        /**
         * 线程睡眠:
         * 当线程调用sleep方法时调用线程会被阻塞挂起指定的时间,在这期间调度器不会去调度该线程.
         */
        Thread.sleep(1000);

        /**
         * 中断线程: 默认中断状态为false, 调用此方法后设置成 true
         */
        thread.interrupt();
        /**
         * 检测当前线程是否被中断: 不清除线程中断状态
         */
        boolean interrupted = thread.isInterrupted();

        /**
         * 检测当前线程是否被中断,并清除线程中断状态
         */
        boolean interrupted1 = Thread.interrupted();
    }

    /**
     * 1.Thread implements Runnable.
     * 2. 然后重写run方法.
     * 3. 调用thread.start的时候会调用native方法 start0.
     * 4.等待虚拟机调用 Runnable.run.
     * 5.然后就执行 Thread.run, Thread的run被子类重写,所以最后调用继承Thread子类.run
     */
    static class CreateByThread extends Thread {

        @Override
        public void run() {
            System.out.println("extends Thread:方式创建线程");
        }
    }

    /**
     * 1.实现Runnable, 重写Runnable.run方法.
     * 2.然后通过new Thread(target = Runnable r).start.
     * 3.然后等待虚拟机调用Thread.run
     * Thread.run的实现:
     * if (target != null) {
     * target.run();
     * }
     * <p>
     * 4.此时target!=null. 所以会调用实现Runnable的run方法
     */
    static class CreateByRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("implements Runnable:方式创建线程");
        }
    }

    /**
     * 分为两种
     * 第一种: new Thread(new FutureTask(new CreateByCallable())).start;
     * 1.通过FutureTask 包装,FutureTask implements RunnableFuture RunnableFuture<V> extends Runnable, Future<V>
     * 2.所以也是一个包装过的Runnable
     * 3.然后等待虚拟机调用Thread.run.然后调用:
     * 4.Runnable.run-->RunnableFuture.run-->FutureTask.run
     * 5.在FutureTask.run方法中调用Callable.call
     * <p>
     * 第二种: ExecutorService.submit(new CreateByCallable())
     * 1.通过线程池的ExecutorService.submit
     * 2.然后在submit中包装成execute(new RunnableFuture(new CreateByCallable()))
     * 3.所以最后执行的是execute
     * 4.等待线程池调度.最后执行 Runnable.run-->RunnableFuture.run-->FutureTask.run方法中调用Callable.call
     */
    static class CreateByCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("implements Callable:方式创建线程");
            return "";
        }
    }
}
