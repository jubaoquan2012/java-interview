package com.interview.javabinterview.highconcurrency.block_queue;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 总结:
 *      1."单向"链表结构,区别LinkedBlockingDeque 是双向操作
 *      2.无界. 但是最大capacity:Integer.MAX_VALUE
 *      3.支持 头尾操作(入队和出队)
 *      4. 利用ReentrantLock(不可可指定fair,默认是非公平),和条件队列 condition,来保证出队和入队的阻塞操作.
 *          两个锁: ReentrantLock takeLock 和 Condition notEmpty = takeLock.newCondition();
 *                 ReentrantLock putLock  和 Condition notFull = putLock.newCondition();
 *      5.api:
 *          add(): 调用offer()
 *          put(): 调用putLast()-->linkLast(),队尾追加;
 *
 * @author Ju Baoquan
 * Created at  2020/5/18
 */
public class LinkedBlockingQueueCodeJu {
    public static void main(String[] args) {

    }
    private void impl() throws InterruptedException {
        LinkedBlockingQueue<Object> blockingDeque = new LinkedBlockingQueue<>();

        blockingDeque.add("");

        blockingDeque.put("");;

        blockingDeque.offer("");

        blockingDeque.offer("",1000, TimeUnit.MILLISECONDS);

        blockingDeque.take();

        blockingDeque.poll();

        blockingDeque.poll(1000, TimeUnit.MILLISECONDS);

        blockingDeque.peek();

        blockingDeque.remove();
    }
}
