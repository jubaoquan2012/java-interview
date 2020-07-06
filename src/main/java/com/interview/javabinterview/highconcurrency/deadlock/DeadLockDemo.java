package com.interview.javabinterview.b_highconcurrency.deadlock;

/**
 * 死锁:
 * 1.互斥条件:         资源排他性,只能被一个线程占用
 * 2.请求并占有资源:    持有至少一个资源再去请求资源
 * 3.占用不可剥夺条件:  获取到的资源,没有释放前不会被其他线程再次获取占用
 * 4.环路等待条件:     互相等待释放资源,形成一个环路等待
 *
 * @author Ju Baoquan
 * Created at  2020/3/7
 */
public class DeadLockDemo {
    //创建资源
    private static Object resourceA = new Object();
    private static Object resourceB = new Object();
    public static void main(String[] args) {
        //创建线程A
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceA) {
                    System.out.println(Thread.currentThread().getName() + "获取到resourceA");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "waiting get resourceB");
                    synchronized (resourceB) {
                        System.out.println(Thread.currentThread().getName() + "获取到resourceB");
                    }
                }
            }
        });
        //创建线程B
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread().getName() + "获取到resourceB");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "waiting get resourceA");
                    synchronized (resourceA) {
                        System.out.println(Thread.currentThread().getName() + "获取到resourceA");
                    }
                }
            }
        });
        threadA.start();
        threadB.start();
    }
}
