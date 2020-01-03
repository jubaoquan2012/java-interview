package com.interview.javabinterview.threadstudy.lock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/10/18
 */
public class LockDemo {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    }
}
