package com.interview.javabinterview.highconcurrency.block_queue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * SynchronousQueue总结:
 *      1.没有容量,不存储元素,每一个put操作,必须要等待take操作,否则不能继续添加元素
 *      2.因为没有容量: peek(),contains(),clear isEmpty..等方法是无效的.
 *      3.分为公平和非公平,默认是非公平的访问策略.  Transferer<E> transferer = fair ? new TransferQueue<E>() : new TransferStack<E>();
 *      4.采用队列TransferQueue来实现公平性策略，采用堆栈TransferStack来实现非公平性策略，他们两种都是通过"链表"实现的，其节点分别为QNode，SNode。
 *
 * @author Ju Baoquan
 * Created at  2020/5/18
 */
public class SynchronousQueueCodeJu {

    public static void main(String[] args) {

    }

    private void impl() throws InterruptedException {
        SynchronousQueue<Object> synchronousQueue = new SynchronousQueue<>();
        synchronousQueue.add("");

        synchronousQueue.put("");

        synchronousQueue.offer("");

        synchronousQueue.offer("", 1000, TimeUnit.MILLISECONDS);

        synchronousQueue.take();

        synchronousQueue.poll();

        synchronousQueue.poll(1000, TimeUnit.MILLISECONDS);

        synchronousQueue.peek();
    }
}
