package com.interview.javabinterview.threadPool;

import java.util.concurrent.TimeUnit;

/**
 * 测试线程的状态
 *
 * @author Ju Baoquan
 * Created at  2019/5/9
 */

public class ThreadStatusDemo {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "threadCreate-1-timedWaiting").start();

        new Thread(() -> {
            while (true) {
                synchronized (ThreadStatusDemo.class) {
                    try {
                        ThreadStatusDemo.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "threadCreate-2-Waiting").start();

        new Thread(new BlockDemo(), "threadCreate-3-block").start();
        new Thread(new BlockDemo(), "threadCreate-4-block").start();

        /**
         * 1.程序运行
         * 2.在 targetclass下找到此ThreadStatusDemo 然后找到文件在D盘的位置: show in explorer
         * 3.然后打开powerShell :文件-->打开windows PowerShell 然后查看状态
         * 4.显示是
         *
         *  threadCreate-1-timedWaiting :
         *      java.lang.Thread.State: TIMED_WAITING (sleeping)
         *
         *  threadCreate-2-Waiting:
         *      java.lang.Thread.State: WAITING (on object monitor)
         *
         *  threadCreate-3-block:
         *      java.lang.Thread.State: TIMED_WAITING (sleeping)
         *      拿到了锁
         *
         *  threadCreate-4-block:
         *      java.lang.Thread.State: BLOCKED (on object monitor)
         *      没有拿到锁
         *
         *      线程的六种状态:
         *      NEW:			没有调用start方法
         *      RUNNABLE:		运行状态
         *      BLOCKED:		阻塞
         *      WAITING:		等待
         *      TIMED_WAITING:	时间等待
         *      TERMINATED:		终止
         */
    }

    static class BlockDemo extends Thread {
        public void run() {
            synchronized (BlockDemo.class) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
