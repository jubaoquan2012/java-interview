package com.interview.javabinterview.socket.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 *
 * @author Ju Baoquan
 * Created at  2020/2/28
 */
public class TCPServerSocket {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        BufferedReader bufferedReader = null;
        try {
            serverSocket = new ServerSocket(8080);
            //等待客户端连接
            Socket socket = serverSocket.accept();
            //获得输入流
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}
