package com.interview.javabinterview.b_highconcurrency.juc;

import com.interview.javabinterview.b_highconcurrency.juc.impl.CountDownLatch;
import com.interview.javabinterview.b_highconcurrency.juc.impl.CyclicBarrier;
import com.interview.javabinterview.b_highconcurrency.juc.impl.ReentrantLock;
import com.interview.javabinterview.b_highconcurrency.juc.impl.ReentrantReadWriteLock;

import java.util.concurrent.Semaphore;

/**
 * AQS
 * 1.抽象的队列同步器,是很多并发工具类的实现基础: 例如: ReentrantLock、ReentrantReadWriteLock、CountDownLatch、Semaphore
 * 2.AQS中,维护了一个volatile int state 变量和一个FIFO线程等待双向队列
 * 3.volatile 能够保证多线程下的可见性,当state = 1 代表当前对象已经被占用,其他线程来加锁时则会失败,然后会被放入到一个FIFO的等待队列队列中,
 * 并且会被park起来,等待其他获取所得线程释放锁才能被唤醒.
 * 4.state的操作时CAS操作,保证安全性
 * 5.FIFO的队列由两种: 独占和共享.
 *
 * @author Ju Baoquan
 * Created at  2020/5/19
 */
public class AQSCodeJu {

    public static void main(String[] args) {

    }

    public void impl() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        reentrantLock.unlock();

        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        countDownLatch.await();
        countDownLatch.countDown();

        Semaphore semaphore = new Semaphore(1);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(1);
    }
}
