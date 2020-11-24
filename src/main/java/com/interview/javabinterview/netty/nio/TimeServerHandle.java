package com.interview.javabinterview.netty.nio;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Description:
 *
 * @author baoquan.Ju
 * Created at  2020/11/19
 */
public class TimeServerHandle implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    /**
     * 初始化多路复用器，绑定监听端口
     *
     * @param port
     */
    public TimeServerHandle(int port) {
        try {
            //step.1 打开ServerSocketChannel，用于监听客户端的连接，他是所有所有客户端连接的父管道
            serverSocketChannel = ServerSocketChannel.open();
            //step.2 监听绑定端口，设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            //step.3 创建Selector（多路复用器），启动线程
            selector = Selector.open();
            //step.4 将ServerSocketChannel注册到Selector中，监听ACCEPT事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The timeServer is start port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        //step.5 多路复用器在线程run（）方法的无限循环体内轮训就绪的key。
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    //
                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    key.cancel();
                    if (key.channel() != null) {
                        key.channel().close();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //step.6 多路复用器监听到有新的客服端接入，处理新的接入请求，完成TCP三次握手，建立物理链路
            if (key.isAcceptable()) {
                //Accept the new connection
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                //Add the new connection to the selector
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            }
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                //allocate 分配
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The TimeServer receive order :" + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                            new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    doWrite(socketChannel, currentTime);
                } else if (readBytes < 0) {
                    //对链路关闭
                    key.cancel();
                    socketChannel.close();
                } else {
                    //读到0字节数据
                }

            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String response) throws IOException {
        if (!StringUtils.isEmpty(response) && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
        }
    }


}
