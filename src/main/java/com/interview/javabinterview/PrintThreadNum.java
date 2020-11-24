package com.interview.javabinterview;

import lombok.Setter;

/**
 * Class Description:
 *
 * @author baoquan.Ju
 * Created at  2020/10/30
 */
public class PrintThreadNum {
    private Object lock = new Object();

    private volatile int count = 0;

    public void printNum() {
        Thread t1 = new Thread(new PrintTask());
        Thread t2 = new Thread(new PrintTask());
        t1.start();
        t2.start();
    }

    class PrintTask implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                synchronized (lock) {
                    try {
                        System.out.println(++count);
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        PrintThreadNum printThreadNum = new PrintThreadNum();
        printThreadNum.printNum();
    }
}
