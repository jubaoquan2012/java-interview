package com.interview.javabinterview.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 客户端
 *
 * @author Ju Baoquan
 * Created at  2020/2/28
 */
public class ClientSocketDemo {
    public static void main(String[] args) {
        try {
            Socket socket =new Socket("127.0.0.1",8080);
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = sin.readLine();
            while (!line.equals("bye")) {
                os.println(line);
                os.flush();
                System.out.println("Client:" + is.readLine());
                System.out.println("Server:" + line);
                line = sin.readLine();
            }
            os.close();
            is.close();
            sin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
