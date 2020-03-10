package com.interview.javabinterview.thread.thread_local;

/**
 * ThreadLocal 不支持继承
 *
 * @author Ju Baoquan
 * Created at  2020/3/7
 */
public class ThreadLocal_Extend_Demo {

    private static ThreadLocal<String> localVariable = new ThreadLocal<String>();

    public static void main(String[] args) {
        //在主线程设置线程变量
        System.out.println("main线程set()");
        localVariable.set("hello word!");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程_" +
                        Thread.currentThread().getName() +
                        ":get():" + localVariable.get());
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()
                    + ":get():" + localVariable.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
