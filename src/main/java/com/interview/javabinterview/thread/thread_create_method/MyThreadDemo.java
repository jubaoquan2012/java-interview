package com.interview.javabinterview.thread.thread_create_method;

import java.util.concurrent.*;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/6
 */
public class MyThreadDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*MyThread1 继承 Thread*/
        MyThread1 thread1 = new MyThread1();
        MyThread2 thread2 = new MyThread2();
        MyThread3 thread3 = new MyThread3();

        /**
         * 继承 Thread 启动方式
         */
        thread1.start();

        /**
         * 实现 Runnable 启动方式
         */
        Thread thread = new Thread(thread2);
        thread.start();

        /**
         * 实现 Callable<V> 启动方法
         */
        FutureTask<String> futureTask = new FutureTask<>(thread3);
        new Thread(futureTask).start();
        String str1 = futureTask.get();

        /**
         * 实现 Callable<V> 启动方法
         */
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(thread3);
        String str2 = future.get();
    }
}
