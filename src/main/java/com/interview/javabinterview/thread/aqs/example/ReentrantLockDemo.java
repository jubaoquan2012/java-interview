package com.interview.javabinterview.thread.aqs.example;

import com.interview.javabinterview.thread.aqs.lockimpl.ReentrantLock;

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
