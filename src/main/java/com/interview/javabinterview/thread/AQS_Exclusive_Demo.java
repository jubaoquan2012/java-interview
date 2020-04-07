package com.interview.javabinterview.thread;

import com.interview.javabinterview.thread.aqs.AbstractQueuedSynchronizer;

import java.util.concurrent.locks.LockSupport;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/11
 */
public class AQS_Exclusive_Demo {

    Node tail;

    Node head;

    public void acquire(int arg) {
        // 获取锁成功直接结束,失败添加到等待队列,自旋的方式获取锁.
        if (!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) {
            selfInterrupt();
        }
    }

    // 从头部获取锁, 如果成功直接返回,失败就进去park
    private boolean acquireQueued(Node node, int arg) {
        boolean interrupted = true;
        for (; ; ) {
            Node p = node.prev;
            if (p == head && tryAcquire(arg)) {
                setHead(node);
                interrupted = false;
                p.next = null;
                return interrupted;
            }
            if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()) {
                interrupted = true;
            }
        }
    }

    private boolean shouldParkAfterFailedAcquire(Node p, Node node) {
        int ws = p.waitStatus;
        if (ws == Node.SIGNAL) {
            return true;
        }
        if (ws > 0) {
            do {
                node.prev = p = p.prev;
            } while (p.waitStatus > 0);
            p.next = node;
        } else {
            compareAndSetStatus(p, ws, Node.SIGNAL);
        }
        return false;
    }

    private boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }

    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        Node pred = tail;
        //尾节点不为null 队尾插入
        if (pred != null) {
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(mode);
        return node;
    }

    private Node enq(Node node) {
        Node t = tail;
        for (; ; ) {
            if (t == null) { //must be init
                if (compareAndSetHead(new Node())) {
                    tail = head;
                }
            } else {
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return node;
                }
            }
        }
    }

    private boolean tryAcquire(int arg) {
        return false;
    }

    //和头部断开,设置当前节点为head.
    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }

    private void compareAndSetStatus(Node p, int except, int update) {
    }

    private boolean compareAndSetHead(Node node) {
        return false;
    }

    private boolean compareAndSetTail(Node pred, Node node) {

        return false;
    }

    private void selfInterrupt() {
        Thread.currentThread().interrupt();
    }

    final static class Node {

        static final Node EXCLUSIVE = null;

        static final int CANCLE = 1;

        static final int SIGNAL = -1;

        static final int CONDITION = -2;

        static final int PROPAGATE = -3;

        volatile int waitStatus;

        volatile Node prev;

        volatile Node next;

        volatile Thread thread;

        volatile Node nextWaiter;

        public Node() {

        }

        public Node(Thread thread, Node node) {

            this.thread = thread;
            this.nextWaiter = node;
        }
    }
}
