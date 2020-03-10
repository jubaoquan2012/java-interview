package com.interview.javabinterview.thread.interrupt;

/**
 * run方法无阻塞
 * @author Ju Baoquan
 * Created at  2020/3/7
 */
public class Thread_Interrupted_Not_Blocked extends Thread {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("run运行");
        }
    }
    public static void main(String[] args) {
        Thread_Interrupted_Not_Blocked td = new Thread_Interrupted_Not_Blocked();
        td.start();
        try {
            Thread.sleep(1000);
            System.out.println("before_interrupt()");
            td.interrupt();
            Thread.sleep(1000);
            System.out.println("thread over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
