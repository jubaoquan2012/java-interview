package com.interview.javabinterview.socket.tcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 客户端
 *
 * @author Ju Baoquan
 * Created at  2020/2/28
 */
public class TCPClientSocket {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello");
        } catch (Exception e) {

        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
