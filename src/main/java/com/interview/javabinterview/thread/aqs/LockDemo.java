package com.interview.javabinterview.thread.aqs;



import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/10
 */
public class LockDemo {

    public static void main(String[] args) throws InterruptedException{
        Lock reentrantLock = new ReentrantLock(true);
        reentrantLock.lock();
        reentrantLock.unlock();

        ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

        Lock readLock = readWriteLock.readLock();
        readLock.lock();
        readLock.unlock();

        Lock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            writeLock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(100);
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(1000);
        blockingQueue.put("s"); //队列满,阻塞添加
        blockingQueue.offer("");

        String take = blockingQueue.take();//队列孔,阻塞获取
        String poll = blockingQueue.poll();
        String peek = blockingQueue.peek();

    }



}
