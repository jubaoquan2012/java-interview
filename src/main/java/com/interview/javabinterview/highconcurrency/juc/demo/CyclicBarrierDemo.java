package com.interview.javabinterview.highconcurrency.juc.demo;

import com.interview.javabinterview.highconcurrency.juc.impl.CyclicBarrier;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/9
 */
public class CyclicBarrierDemo {

    static class TaskThreads extends Thread {

        CyclicBarrier barrier;

        public TaskThreads(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(getName() + " 到达栅栏 A");
                barrier.await();
                System.out.println(getName() + " 冲破栅栏 A");

                Thread.sleep(2000);
                System.out.println(getName() + " 到达栅栏 B");
                barrier.await();
                System.out.println(getName() + " 冲破栅栏 B");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int threadNum = 5;
        CyclicBarrier barrier = new CyclicBarrier(threadNum, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "完成最后任务");
            }
        });
        for (int i = 0; i < threadNum; i++) {
            new TaskThreads(barrier).start();
        }
    }
}
