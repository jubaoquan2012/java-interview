package com.interview.javabinterview.b_highconcurrency.thread.demo;

/**
 * run方法有阻塞
 *
 * @author Ju Baoquan
 * Created at  2020/5/20
 */
public class Thread_Interrupted_Blocked extends Thread {

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 如果线程在调用 Object 类的 wait()、wait(long)、join()、join(long)、sleep(long)方法过程中受阻,
     * 则其isInterrupt状态将被清除，设成false,它还将收到一个InterruptedException.
     */
    public static void main(String[] args) {
        Thread_Interrupted_Blocked td = new Thread_Interrupted_Blocked();
        td.start();
        try {
            Thread.sleep(1000);
            System.out.println("before_interrupt()");
            td.interrupt();// 会在此处抛出异常:java.lang.InterruptedException: sleep interrupted
            Thread.sleep(1000);
            System.out.println("thread over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}