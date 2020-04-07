package com.interview.javabinterview.thread.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/17
 */
public class ReentrantLockConditionDemo {

    static Lock lock = new ReentrantLock();

    static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        lock.lock();
        new Thread(new SignalThread()).start();
        System.out.println("主线程等待通知");
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println("主线程恢复运行");
    }

    public static class SignalThread implements Runnable {

        @Override
        public void run() {
            try {
                condition.signal();
                System.out.println("子线程通知");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
