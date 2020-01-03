package com.interview.javabinterview.threadstudy.LockSupport;


import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/10/18
 */
public class LockSupportDemo {

    @Test
    public void test1() {
        System.out.println("begin park");
        //第一次调用park 默认是没有拿到许可证的,所以阻塞线程不会直接返回
        LockSupport.park();
        System.out.println("end park");
    }

    @Test
    public void test2() {
        System.out.println("begin park");
        //使用当前线程获取到许可证
        LockSupport.unpark(Thread.currentThread());
        //再次调用park方法
        LockSupport.park();
        System.out.println("end park");
    }

    @Test
    public void test3() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("child thread begin park");
                //调用park 方法,挂起自己
                LockSupport.park();
//                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
                System.out.println("child thread unpark!");
            }
        });
        //启动子线程
        thread.start();
        Thread.sleep(1000);
        System.out.println("main thread begin unpark!");
        //调用unpark 方法让thread线程持有许可证,然后park方法返回
        LockSupport.unpark(thread);
    }

    @Test
    public void test4() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("child thread begin park");
                //调用park 方法,挂起自己

                while (!Thread.currentThread().isInterrupted()){
                    LockSupport.park();
                }
                System.out.println("child thread unpark!");
            }
        });
        //启动子线程
        thread.start();
        Thread.sleep(1000);
        System.out.println("main thread begin unpark!");
        //调用unpark 方法让thread线程持有许可证,然后park方法返回
        thread.interrupt();
    }
}
