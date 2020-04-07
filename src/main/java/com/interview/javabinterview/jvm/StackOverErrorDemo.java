package com.interview.javabinterview.jvm;

/**
 * 栈溢出: java.lang.StackOverflowError
 * <p>
 * VM Args:-Xss110k  -XX:+PrintGCDetails
 * <p>
 * 单个线程请求栈深度大于虚拟机所允许的最大深度（如常用的递归调用层级过深等），
 * 再比如单个线程定义了大量的本地变量，导致方法帧中本地变量表长度过大等也会导致
 * StackOverflowError 异常， 一句话：在单线程下，当栈桢太大或虚拟机容量太小导
 * 致内存无法分配时，都会发生 StackOverflowError 异常
 *
 * @author Ju Baoquan
 * Created at  2020/3/25
 */
public class StackOverErrorDemo {

    private void dontStop() {
        dontStop();
    }

    public void stackLeakByThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                dontStop();
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        StackOverErrorDemo oom = new StackOverErrorDemo();
        oom.stackLeakByThread();
    }
}
