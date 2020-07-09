package com.interview.javabinterview.highconcurrency.block_queue;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 总结:
 *      1."双向"链表结构
 *      2.无界. 但是最大capacity:Integer.MAX_VALUE
 *      3.支持 头尾操作(入队和出队)
 *      4. 利用ReentrantLock(不可可指定fair,默认是非公平)和条件队列 condition,来保证出队和入队的阻塞操作.
 *      5.api:
 *          add(): 调用addLast()-->offerLast()
 *          put(): 调用putLast()-->linkLast(),队尾追加;
 *
 * @author Ju Baoquan
 * Created at  2020/5/18
 */
public class LinkedBlockingDueueCodeJu {
    public static void main(String[] args) {

    }
    private void impl() throws InterruptedException {
        LinkedBlockingDeque<Object> blockingDeque = new LinkedBlockingDeque<>();
        blockingDeque.add("");
        blockingDeque.addFirst("");
        blockingDeque.addLast("");

        blockingDeque.put("");;
        blockingDeque.putFirst("");
        blockingDeque.putLast("");


        blockingDeque.offer("");
        blockingDeque.offerFirst("");
        blockingDeque.offerLast("");


        blockingDeque.offer("",1000, TimeUnit.MILLISECONDS);
        blockingDeque.offerFirst("",1000, TimeUnit.MILLISECONDS);
        blockingDeque.offerLast("",1000, TimeUnit.MILLISECONDS);

        blockingDeque.take();
        blockingDeque.takeFirst();
        blockingDeque.takeLast();

        blockingDeque.poll();
        blockingDeque.pollFirst();
        blockingDeque.pollLast();

        blockingDeque.poll(1000, TimeUnit.MILLISECONDS);
        blockingDeque.pollFirst(1000, TimeUnit.MILLISECONDS);
        blockingDeque.pollLast(1000, TimeUnit.MILLISECONDS);

        blockingDeque.peek();
        blockingDeque.peekFirst();
        blockingDeque.peekLast();

        blockingDeque.remove();
    }
}
