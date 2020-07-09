package com.interview.javabinterview.highconcurrency;

import com.interview.javabinterview.highconcurrency.juc.impl.ReentrantReadWriteLock;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/18
 */
public class Demo {

    public static void main(String[] args) {
        System.out.println(121195181 /100%40);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();


        lock.writeLock().lock();
    }
}
