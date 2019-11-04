package com.interview.javabinterview.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock(false);
        reentrantLock.lock();
    }
}
