package com.interview.javabinterview.sychronized;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/9/9
 */
public class Something {

    private ReentrantLock reentrantLock = new ReentrantLock();

    public void isSyncA() {
        reentrantLock.lock();
        function();
        reentrantLock.unlock();

    }

    public synchronized void isSyncB() {

        function();
    }

    public static synchronized void cSyncA() {
        function();
    }

    public static synchronized void cSyncB() {
        function();
    }

    public static void function() {
        int count = 5;
        for (int i = 0; i < 5; i++) {
            count--;
            System.out.println(Thread.currentThread().getName() + " - " + count);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }
}
