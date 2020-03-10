package com.interview.javabinterview.thread.thread_local;

/**
 * ThreadLocal 工作原理演示
 *
 * @author Ju Baoquan
 * Created at  2020/3/7
 */
public class ThreadLocalDemo {

    private static ThreadLocal<String> localVariable = new ThreadLocal<String>();

    private static void print(String string) {
        System.out.println(string + ":" + localVariable.get());
        //清除当前线程本地内存中的localVariable变量
        localVariable.remove();
    }

    public static void main(String[] args) {
        Thread thread_1 = new Thread(new Runnable() {
            @Override
            public void run() {
                localVariable.set("设置 thread_1");
                print("thread_1");
                System.out.println("thread_1 remove after:" + localVariable.get());
            }
        }, "thread_1");

        Thread thread_2 = new Thread(new Runnable() {
            @Override
            public void run() {
                localVariable.set("设置 thread_2");
                print("thread_2");
                System.out.println("thread_2 remove after:" + localVariable.get());
            }
        }, "thread_2");
        thread_1.start();
        thread_2.start();
    }
}
