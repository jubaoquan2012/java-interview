package com.interview.javabinterview.thread;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/12
 */
public class AQS_Shared_Demo {

    Node tail;

    Node head;

    private void acquire(int arg) {
        if (tryAcquireShared(arg) < 0) {
            doAcquireShared(arg);
        }
    }

    private void doAcquireShared(int arg) {

    }

    private int tryAcquireShared(int arg) {
        throw new UnsupportedOperationException();
    }

    static final class Node {

        static final Node SHARED = new Node();

        static final AQS_Exclusive_Demo.Node EXCLUSIVE = null;

        static final int CANCLE = 1;

        static final int SIGNAL = -1;

        static final int CONDITION = -2;

        static final int PROPAGATE = -3;

        volatile int waitStatus;

        volatile AQS_Exclusive_Demo.Node prev;

        volatile AQS_Exclusive_Demo.Node next;

        volatile Thread thread;

        volatile AQS_Exclusive_Demo.Node nextWaiter;

        public Node() {

        }

        public Node(Thread thread, AQS_Exclusive_Demo.Node node) {
            this.thread = thread;
            this.nextWaiter = node;
        }
    }
}
