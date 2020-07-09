package com.interview.javabinterview.highconcurrency.juc.demo;

import com.interview.javabinterview.highconcurrency.juc.impl.CountDownLatch;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/1/3
 */
public class CountDownLatchDemo {

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程开始....");

        Thread thread = new Thread(new Worker());
        thread.start();

        System.out.println("主线程等待");
        System.out.println(latch.toString());
        latch.await();
        System.out.println(latch.toString());
        System.out.println("主线程继续运行");
    }

    public static class Worker implements Runnable {

        @Override
        public void run() {
            System.out.println("子线程任务正在执行");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }
}
