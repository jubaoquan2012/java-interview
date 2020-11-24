package com.interview.javabinterview.netty.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description:
 * 2.1.2 同步阻塞IO创建的的TimeServer源码分析
 *
 * @author baoquan.Ju
 * Created at  2020/11/12
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        //负责绑定ip，启动监听端口
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The TimeServer is start server port:" + port);
            //负责发起连接操作
            Socket socket = null;
            while (true) {
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
