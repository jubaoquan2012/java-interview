package com.interview.javabinterview.thread.aqs.demo;

import com.interview.javabinterview.thread.aqs.Condition;
import com.interview.javabinterview.thread.queue.LinkedBlockingDeque;
import com.interview.javabinterview.thread.queue.Queue;


public class ProducerAndConsumerDemo {

    final static NonReentrantLock lock = new NonReentrantLock();
    final static Condition notFull = lock.newCondition();
    final static Condition notEmpty = lock.newCondition();

    final static Queue<String> queue = new LinkedBlockingDeque<String>();
    final static int queueSize = 10;

    public static void main(String[] args) {
        Thread produer = new Thread(new Runnable() {
            @Override
            public void run() {
                //获取独占锁
                lock.lock();
                try {
                    //如果队列满了就等待
                    while (queue.size() == queueSize) {
                        notEmpty.await();
                    }
                    queue.add("ele");
                    notFull.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    while (0 == queue.size()) {
                        notFull.await();
                    }
                    String ele = queue.poll();
                    System.out.println(ele);
                    notEmpty.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        produer.start();
        consumer.start();
    }
}
