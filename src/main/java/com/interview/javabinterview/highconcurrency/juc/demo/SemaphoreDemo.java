package com.interview.javabinterview.b_highconcurrency.juc.demo;

import java.util.concurrent.Semaphore;

/**
 * 从程序角度看，停车场就相当于信号量Semaphore，其中许可数为5，车辆就相对线程。当来一辆车时，许可数就会减 1 ，
 * 当停车场没有车位了（许可书 == 0 ），其他来的车辆需要在外面等候着。如果有一辆车开出停车场，许可数 + 1，然后放进来一辆车。
 * <p>
 * 信号量Semaphore是一个非负整数（>=1）。当一个线程想要访问某个共享资源时，它必须要先获取Semaphore，当Semaphore >0时，
 * 获取该资源并使Semaphore – 1。如果Semaphore值 = 0，则表示全部的共享资源已经被其他线程全部占用，线程必须要等待其他线程释放资源。
 * 当线程释放资源时，Semaphore则+1
 *
 * @author Ju Baoquan
 * Created at  2020/5/19
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Parking parking = new Parking(3);
        for (int i = 0; i < 5; i++) {
            new Car(parking).start();
        }
    }

    static class Parking {

        private Semaphore semaphore;

        Parking(int count) {
            semaphore = new Semaphore(count);
        }

        public void park() {
            try {
                semaphore.acquire();
                long time = (long) (Math.random() * 10);
                System.out.println(Thread.currentThread().getName() + "进入停车场,停车" + time + "秒....");
                Thread.sleep(time*1000);
                System.out.println(Thread.currentThread().getName() + "开出停车场");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }

    static class Car extends Thread {

        private Parking parking;

        public Car(Parking parking) {
            this.parking = parking;
        }

        @Override
        public void run() {
            parking.park();
        }
    }
}
