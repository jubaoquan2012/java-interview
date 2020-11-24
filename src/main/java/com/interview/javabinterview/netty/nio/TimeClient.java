package com.interview.javabinterview.netty.nio;

/**
 * Description:
 *
 * @author baoquan.Ju
 * Created at  2020/11/19
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8080;

        new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();
    }
}
