package com.interview.javabinterview.thread.thread_method;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/9
 */
public class JoinTaskDemo {

    public static void main(String[] args) throws InterruptedException {
        JoinTask thread1 = new JoinTask();
        JoinTask thread2 = new JoinTask();
        thread1.start();
        thread2.start();
        thread2.join();
        thread1.join();

        System.out.println("nihao");
        TimeUnit.SECONDS.sleep(10);
    }

    public static class JoinTask extends Thread {

        @Override
        public void run() {
            System.out.println("");
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName()+"_执行_" + i);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
