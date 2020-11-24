package com.interview.javabinterview.netty.nio;

/**
 * Description:
 *
 * @author baoquan.Ju
 * Created at  2020/11/19
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        new Thread(new TimeServerHandle(port), "NIO-MultipleTimeServer-001").start();
    }
}
