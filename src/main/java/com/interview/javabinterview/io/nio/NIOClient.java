package com.interview.javabinterview.io.nio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * Block IO 同步阻塞Io
 *
 * @author Ju Baoquan
 * Created at  2020/6/8
 */
public class NIOClient {

    public static void main(String[] args) throws IOException {

        Socket client = new Socket("localhost", 8080);

        OutputStream os = client.getOutputStream();
        String message = UUID.randomUUID().toString();

        System.out.println("客户端发送数据:" + message);
        os.write(message.getBytes());
        os.close();
        client.close();
    }
}
