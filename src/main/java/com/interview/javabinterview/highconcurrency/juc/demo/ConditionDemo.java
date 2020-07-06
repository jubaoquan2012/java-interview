package com.interview.javabinterview.b_highconcurrency.juc.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 条件队列的
 *
 * @author Ju Baoquan
 * Created at  2020/5/19
 */
public class ConditionDemo {

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Condition condition = lock.newCondition();

        new Thread(() -> {
            try {
                lock.lock();
                System.out.println("1.线程一加锁成功");
                System.out.println("2.线程一执行await被挂起");
                condition.await();
                System.out.println("7.线程一被唤醒,是否持有锁:"+lock.isLocked());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("8.线程一释放成功");
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();
                System.out.println("3.线程二加锁成功");
                System.out.println("4.线程二唤醒线程一");
                condition.signal();
                System.out.println("5.线程二被唤醒");
            } finally {
                lock.unlock();
                System.out.println("6.线程二释放成功");
            }
        }).start();
    }
}
