package com.interview.javabinterview.algorithm_code.thread;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 * 两个线程交替打印
 *
 * @author baoquan.Ju
 * Created at  2020/11/3
 */
public class ThreadPrint {

    private volatile int count = 0;

    public void print() {
        Runnable printTask = new Runnable() {
            @Override
            public void run() {

            }
        };
    }

    public static void main(String[] args) {
        ThreadPrint threadPrint = new ThreadPrint();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    this.notify();
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
