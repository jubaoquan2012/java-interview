package com.interview.javabinterview.threadstudy.LockSupport;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * 先进先出的锁
 *
 * @author Ju Baoquan
 * Created at  2019/10/18
 */
public class FIFOMutex {
    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<Thread>();

    public void lock() {
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        //只有队首的线程可以获取锁
        while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
            //如果当前线程不是队首,则挂起自己
            LockSupport.park(this);
            if (Thread.interrupted()) {
                //如果当前线程是被其他线程中断,则设置标志位为 true
                wasInterrupted = true;
            }

            waiters.remove();
            if (wasInterrupted) {
                //如果当前线程是被其他线程中断的则中断
                current.interrupt();
            }
        }
    }

    public void unlock() {
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }
}
