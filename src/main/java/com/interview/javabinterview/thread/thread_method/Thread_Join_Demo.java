package com.interview.javabinterview.thread.thread_method;



/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/6
 */
public class Thread_Join_Demo extends Thread {

    private int n;
    private Thread previousThread;

    public Thread_Join_Demo(int n, Thread previousThread) {
        this.n = n;
        this.previousThread = previousThread;
    }

    @Override
    public void run() {
        try {
            previousThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <= 20; i++) {
            System.out.println("第" + n + "次打印_" + i);
        }
    }

    public static void main(String[] args) {
        Thread currentThread = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread_Join_Demo joinDemo = new Thread_Join_Demo(i, currentThread);
            joinDemo.start();
            currentThread = joinDemo;
        }
    }
}
