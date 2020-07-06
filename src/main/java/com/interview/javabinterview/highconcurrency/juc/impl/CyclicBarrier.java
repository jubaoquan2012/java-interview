package com.interview.javabinterview.b_highconcurrency.juc.impl;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrier {

    private static class Generation {
        boolean broken = false;
    }

    /** 同步操作锁 */
    private final ReentrantLock lock = new ReentrantLock();

    /** 线程拦截器 */
    private final Condition trip = lock.newCondition();

    /** 每次拦截的线程数 */
    private final int parties;

    /** 换代前执行的任务 */
    private final Runnable barrierCommand;

    /** 表示栅栏的当前年代 */
    private CyclicBarrier.Generation generation = new CyclicBarrier.Generation();

    /** 计数器 */
    private int count;

    private void nextGeneration() {
        // signal completion of last generation
        trip.signalAll();
        // set up next generation
        count = parties;
        generation = new CyclicBarrier.Generation();
    }

    private void breakBarrier() {
        generation.broken = true;
        count = parties;
        trip.signalAll();
    }

    //核心等待方法
    private int dowait(boolean timed, long nanos)
            throws InterruptedException, BrokenBarrierException,
            TimeoutException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            //分代
            final CyclicBarrier.Generation g = generation;
            //检查当前栅栏是否被打翻
            if (g.broken) {
                throw new BrokenBarrierException();
            }

            //如果线程中断，终止CyclicBarrier
            if (Thread.interrupted()) {
                /**
                 * 如果当前线程被打断会做以下三件事
                 * 1.打翻当前栅栏
                 * 2.唤醒拦截的所有线程
                 * 3.抛出中断异常
                 */
                breakBarrier();
                throw new InterruptedException();
            }
            //每次将计数器的值减1
            int index = --count;
            //计数器减为0,需要唤醒所有线程并准换到下一代
            //count == 0 表示所有线程均已到位，触发Runnable任务
            if (index == 0) {  // tripped
                boolean ranAction = false;
                try {
                    //唤醒所有线程钱先执行指定的任务
                    final Runnable command = barrierCommand;
                    if (command != null) {
                        command.run();
                    }
                    ranAction = true;
                    //唤醒所有线程并转到下一代
                    nextGeneration();
                    return 0;
                } finally {
                    //确保在任务未成功执行时能将所有线程唤醒
                    if (!ranAction) {
                        breakBarrier();
                    }
                }
            }

            // loop until tripped, broken, interrupted, or timed out
            //如果计数器不为0,则执行此循环
            for (; ; ) {
                try {
                    //如果不是超时等待，则调用Condition.await()方法等待
                    if (!timed) {
                        trip.await();
                    } else if (nanos > 0L) {
                        //超时等待，调用Condition.awaitNanos()方法等待
                        nanos = trip.awaitNanos(nanos);
                    }
                } catch (InterruptedException ie) {
                    //若当前线程在等待期间被中断则打翻栅栏唤醒其他线程
                    if (g == generation && !g.broken) {
                        breakBarrier();
                        throw ie;
                    } else {
                        // We're about to finish waiting even if we had not
                        // been interrupted, so this interrupt is deemed to
                        // "belong" to subsequent execution.
                        //若在捕获中断异常前已经完成在栅栏上的等待,则直接使用中断操作
                        Thread.currentThread().interrupt();
                    }
                }
                //如果线程因为打翻栅栏操作而被唤醒则抛出异常
                if (g.broken) {
                    throw new BrokenBarrierException();
                }

                //如果线程因为换代操作而被唤醒则返回计数器的值
                if (g != generation) {
                    return index;
                }

                //如果线程因为时间到了而被唤醒则打翻栅栏并抛出异常
                if (timed && nanos <= 0L) {
                    breakBarrier();
                    throw new TimeoutException();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public CyclicBarrier(int parties, Runnable barrierAction) {
        if (parties <= 0) {
            throw new IllegalArgumentException();
        }
        this.parties = parties;
        this.count = parties;
        this.barrierCommand = barrierAction;
    }

    public CyclicBarrier(int parties) {
        this(parties, null);
    }

    public int getParties() {
        return parties;
    }

    //非定时等待
    public int await() throws InterruptedException, BrokenBarrierException {
        try {
            return dowait(false, 0L);
        } catch (TimeoutException toe) {
            throw new Error(toe); // cannot happen
        }
    }

    //定时等待
    public int await(long timeout, TimeUnit unit)
            throws InterruptedException,
            BrokenBarrierException,
            TimeoutException {
        return dowait(true, unit.toNanos(timeout));
    }

    public boolean isBroken() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return generation.broken;
        } finally {
            lock.unlock();
        }
    }

    public void reset() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            breakBarrier();   // break the current generation
            nextGeneration(); // start a new generation
        } finally {
            lock.unlock();
        }
    }

    public int getNumberWaiting() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return parties - count;
        } finally {
            lock.unlock();
        }
    }
}
