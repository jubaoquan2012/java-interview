package com.interview.javabinterview.highconcurrency.juc.demo;

import com.interview.javabinterview.highconcurrency.juc.impl.ReentrantLock;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/1/4
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(false);
        lock.lock();
        lock.unlock();
    }
}
