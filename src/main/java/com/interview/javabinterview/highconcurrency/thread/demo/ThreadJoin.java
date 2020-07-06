package com.interview.javabinterview.b_highconcurrency.thread.demo;

/**
 * 演示join方法
 *
 * @author Ju Baoquan
 * Created at  2020/5/20
 */
public class ThreadJoin extends Thread {

    int num;

    Thread previousThread;

    public ThreadJoin(int num, Thread previousThread) {
        this.previousThread = previousThread;
        this.num = num;
    }

    @Override
    public void run() {
        try {
            previousThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("num:" + num
                + ":preThreadName" + previousThread.getName()
                + ":currentThreadName" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Thread previousThread = Thread.currentThread();
        previousThread.setName("第一个线程");
        for (int num = 1; num < 100; num++) {
            ThreadJoin threadJoin = new ThreadJoin(num, previousThread);
            threadJoin.setName("thread:" + num);
            threadJoin.start();
            previousThread = threadJoin;
        }
    }
}
