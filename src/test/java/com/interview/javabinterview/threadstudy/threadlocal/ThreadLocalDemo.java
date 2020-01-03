package com.interview.javabinterview.threadstudy.threadlocal;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/10/17
 */
public class ThreadLocalDemo {

    //public static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("hello Word!");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //在子线程获取存入的值
                System.out.println("thread:" + threadLocal.get());
            }
        });
        thread.start();

        //在主线程获取存入的值
        System.out.println("main:" + threadLocal.get());
    }
}
