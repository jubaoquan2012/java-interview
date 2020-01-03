package com.interview.javabinterview.threadstudy.DeadThread;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/10/17
 */
public class DeadThreadDemo {

    private static volatile Object resourceA = new Object();
    private static volatile Object resourceB = new Object();

    public static void main(String[] args) throws InterruptedException {

        ThreadLocal threadLocal = new ThreadLocal();
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceA) {
                    System.out.println(Thread.currentThread() + "get resourceA");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread() + "waiting get resourceB");

                    synchronized (resourceB) {
                        System.out.println(Thread.currentThread() + "get resourceB");
                    }
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread() + "get resourceB");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread() + "waiting get resourceA");

                    synchronized (resourceA) {
                        System.out.println(Thread.currentThread() + "get resourceA");
                    }
                }
            }
        });

        threadA.start();
        threadB.start();
        System.out.println("main over");
    }
}
