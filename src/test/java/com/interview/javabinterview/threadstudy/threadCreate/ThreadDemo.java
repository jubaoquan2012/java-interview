package com.interview.javabinterview.threadstudy.threadCreate;

import java.util.concurrent.*;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/10/17
 */
public class ThreadDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadByThread threadByThread = new ThreadByThread();
        ThreadByRunnable threadByRunnable = new ThreadByRunnable();
        ThreadByCallable threadByCallable = new ThreadByCallable();

        /**
         * 第一种启动方式
         */
        threadByThread.start();
        /**
         * 第二种启动方式
         */
        new Thread(threadByRunnable).start();

        /**
         * 第三种启动方式
         */
        FutureTask<String> futureTaskByFuture = new FutureTask<>(threadByCallable);
        new Thread(futureTaskByFuture).start();//
        String result = futureTaskByFuture.get();

        /**
         * 第四种方式
         */
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> futureTaskByExecutors = executorService.submit(threadByCallable);
        String s = futureTaskByExecutors.get();
    }
}
