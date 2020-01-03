package com.interview.javabinterview.threadstudy.threadCreate;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/10/17
 */
public class ThreadByThread extends Thread {
    @Override
    public void run() {
        System.out.println("创建线程成功");
    }
}
