package com.interview.javabinterview.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/8
 */
public class BIOServer {

    //服务端网络BIO模型的封装对象
    ServerSocket server;

    public BIOServer(int port) {
        try {
            /**
             * Tomcat 默认端口号: 8080
             * Redis 6379
             * Zookeeper 2181
             * HBase
             * 只要是Java,底层都是ServerSocket();
             */
            server = new ServerSocket(port);// TCP
            System.out.println("BIO 服务已启动,端口号是:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        //循环监听
        while (true) {
            try {
                /**
                 * 等待客户链接,"阻塞"方法
                 *
                 * Socket:数据发送端正在服务端的引用
                 */
                Socket client = server.accept();
                InputStream is = client.getInputStream();
                byte[] buff = new byte[1024];
                int len = is.read(buff);
                if (len > 0) {
                    String msg = new String(buff, 0, len);
                    System.out.println("收到" + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new BIOServer(8080).listen();
    }
}
