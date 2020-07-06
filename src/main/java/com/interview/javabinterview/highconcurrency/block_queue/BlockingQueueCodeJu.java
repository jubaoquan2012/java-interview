package com.interview.javabinterview.b_highconcurrency.block_queue;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * BlockingQueue 接口详解
 * 非阻塞: add(), offer(), poll(), peek()
 * 阻塞:   put(), take(), offer(timeout,unit), poll(timeout,unit)
 * @author Ju Baoquan
 * Created at  2020/5/18
 *
 * todo 阻塞非阻塞总结的不对
 */
public class BlockingQueueCodeJu {

    /**
     * 出队:
     * boolean add(E e);
     *
     * boolean put(E e)throws InterruptedException;
     *
     * boolean offer(E e);
     *
     * boolean offer(E e, long timeout, TimeUnit unit)throws InterruptedException;
     *
     * 入队:
     * E take()throws InterruptedException;
     *
     * E poll();
     *
     * E poll(long timeout, TimeUnit unit)throws InterruptedException;
     *
     * E peek();
     *
     * E remove();
     */
    private Queue queue;

    /**BlockingQueue<E> extends Queue<E>*/
    private BlockingQueue blockingQueue;

    private BlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<Long>(1000);

    private BlockingQueue linkedBlockQueue = new LinkedBlockingQueue<>();

    private BlockingQueue SynchronousQueue =new SynchronousQueue();

    private BlockingQueue priorityBlockQueue = new PriorityBlockingQueue<Long>();

    /**
     * 阻塞队列接口类:
     * 入队 操作:
     *
     * 添加成功返回true,失败抛IllegalStateException异常
     * boolean add(E e);
     *
     * 成功返回 true，队列已满，则返回 false（如果添加了时间参数，且队列已满也会阻塞）
     * boolean offer(E e);
     *
     * boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException;
     *
     * 将元素插入此队列的尾部，如果该队列已满，则一直阻塞
     * void put(E e) throws InterruptedException;
     *
     * ----------------------------------------------------------------------------------------
     *
     * 出队 操作
     *
     * 获取并移除此队列头元素，若没有元素则一直阻塞。
     * E take() throws InterruptedException;
     *
     * 获取并移除此队列的头元素，若队列为空，则返回 null（如果添加了时间参数，且队列中没有数据也会阻塞）
     * E poll();
     *
     * E poll(long timeout, TimeUnit unit)  throws InterruptedException;
     *
     * 获取但不移除此队列的头；若队列为空，则返回 null
     * E peek();
     *
     * 移除指定元素,成功返回true，失败返回false
     * boolean remove(Object o);
     *
     * --------------------------------------------------------------------------------------
     * 实现:
     *
     * 1.基于数组
     * 2.有界: 初始化必须指定长度
     * 3.遵循FIFO原则
     * 4.通过一个锁lock( ReentrantLock),控制 takes 和 puts 操作: 区别于LinkedBlockQueue
     * {@link ArrayBlockingQueueCodeJu}
     *
     *  通过 两个锁(ReentrantLock) takeLock、putLock 分离:控制 takes 和 puts 操作: 区别于ArrayBlockingQueue
     * {@link LinkedBlockingQueueCodeJu}
     *
     * {@link SynchronousQueueCodeJu}
     *
     * {@link PriorityBlockingQueueCodeJu}
     */
}
