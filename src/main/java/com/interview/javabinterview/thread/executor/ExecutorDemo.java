package com.interview.javabinterview.thread.executor;

import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/20
 */
public class ExecutorDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        threadPoolExecutor.execute(new MyThread());
        threadPoolExecutor.shutdown();
        threadPoolExecutor.shutdownNow();
        threadPoolExecutor.allowCoreThreadTimeOut(true);

        Future<String> submit = threadPoolExecutor.submit(new MyCallable());
        String s1 = submit.get();

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new MyThread());
        // Future<?> future = fixedThreadPool.submit(new MyThread());
        Future<String> future = executorService.submit(new MyCallable());
        String s2 = future.get();
    }

    public static class MyThread implements Runnable {

        @Override
        public void run() {
            System.out.println("测试类");
        }
    }

    public static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "返回结果";
        }
    }
}
