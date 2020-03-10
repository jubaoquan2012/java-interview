package com.interview.javabinterview.thread.juc.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Ju Baoquan
 * Created at  2020/3/9
 */
public class LockSupportDemo {

    /**
     * 测试一: 先唤醒线程，在阻塞线程，线程不会真的阻塞；
     * 测试二: 但是先唤醒线程两次再阻塞两次时就会导致线程真的阻塞
     */
    public static void main(String[] args) throws InterruptedException {
        Thread parkThread = new Thread(new ParkThread());
        parkThread.start();
        for (int i = 0; i < 2; i++) {
            System.out.println("开始线程唤醒");
            LockSupport.unpark(parkThread);
            System.out.println("结束线程唤醒");
        }
    }

    public static class ParkThread implements Runnable {

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 2; i++) {
                System.out.println("开始线程阻塞");
                LockSupport.park();
                System.out.println("结束线程阻塞");
            }
        }
    }
}
