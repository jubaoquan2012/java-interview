package com.interview.javabinterview.b_highconcurrency.block_queue;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 总结:
 *   1.数据结构: 数组实现
 *   2.有界阻塞队列
 *   3.FIFO原则
 *   4. 利用ReentrantLock(可指定fair)和条件队列 condition,来保证出队和入队的阻塞操作. 构造函数可以指定公平策略(fair = true),默认是false非公平
 *   5.api :
 *      1).入队: add(): 调用offer,成功返回true,队列已满，则抛出 IllegalStateException
 *              put(): 调用enqueue,尾部插入，如果该队列已满，则等待可用的空间
 *              offer(): 调用enqueue,尾部插入,成功返回true,队列已满返回false.
 *              offer(timeout):调用enqueue,尾部插入,成功返回true,队列已满,则在到达超时时间之前等待队列可用空间. 超时返回false
 *      2).出队:
 *              take():
 *              poll(): 调用enqueue,获取并移除此队列的头，如果此队列为空，则返回 null
 *              poll(timeout): 获取并移除此队列的头，如果此队列为空，在指定的等待时间前等待可用的元素,超时则返回 null
 *              peek():获取队列的头元素,但不移除
 *              remove(object o):从此队列中移除指定元素的单个实例（如果存在）
 *
 * @author Ju Baoquan
 * Created at  2020/5/18
 */
public class ArrayBlockingQueueCodeJu {

    public static void main(String[] args) {

    }

    private void impl() throws InterruptedException {
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1000);
        /**
         * 非阻塞:
         * 将指定的元素插入到此队列的尾部（如果立即可行且不会超过该队列的容量），
         * 在成功时返回 true，如果此队列已满，则抛出 IllegalStateException
         */
        boolean add = blockingQueue.add("");

        /**
         * 非阻塞:
         * 将指定的元素插入到此队列的尾部（如果立即可行且不会超过该队列的容量），
         * 在成功时返回 true，如果此队列已满，则返回 false
         */
        boolean offer = blockingQueue.offer("");

        /**
         * 阻塞:
         * 将指定的元素插入此队列的尾部，如果该队列已满，
         * 则在到达指定的等待时间之前等待可用的空间(阻塞)
         */
        boolean offer1 = blockingQueue.offer("", 1000, TimeUnit.MILLISECONDS);

        /**
         * 阻塞:
         * 将指定的元素插入此队列的尾部，如果该队列已满，则等待可用的空间(阻塞)
         */
        blockingQueue.put("");

        /**
         * 阻塞:
         * 调用dequeue(). 会移除元素
         */
        String take = blockingQueue.take();

        /**
         * 非阻塞:
         * 调用dequeue(). 会移除元素
         */
        String poll = blockingQueue.poll();

        /**
         * 阻塞:
         * 获取并移除此队列的头部，在指定的等待时间前等待可用的元素（如果有必要）
         */
        String poll1 = blockingQueue.poll(1000,TimeUnit.MILLISECONDS);

        /**
         * 非阻塞:
         * 返回元素,不会移除元素
         */
        blockingQueue.peek();

        blockingQueue.remove("");
    }
}
