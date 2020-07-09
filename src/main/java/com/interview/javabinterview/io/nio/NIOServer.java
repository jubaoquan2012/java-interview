package com.interview.javabinterview.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO操作过于繁琐,于是才有了Netty
 * @author Ju Baoquan
 * Created at  2020/6/8
 */
public class NIOServer {

    private int port;

    private boolean block = false;

    // 轮询器 Selector
    private Selector selector;

    //缓冲器 buffer
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    //初始化
    public NIOServer(int port, boolean block) {
        try {
            this.port = port;
            this.block = block;
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(this.port));
            // NIO是BIO的升级,为了兼容BIO,NIO模型默认是采用阻塞是通信
            server.configureBlocking(this.block);
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        System.out.println("listen on" + this.port + ".");
        //轮询主流程
        while (true) {
            try {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                /**
                 * 不断迭代
                 * 同步体现在这里,因为每次只能拿一个key,每次只能处理一种状态
                 */
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    //每一个key代表一种状态
                    process(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 每一次轮询就是调用一次process方法,而每一次调用,只能干一件事
     * 在同一时间点,只能干一件事
     * @param key
     * @throws Exception
     */
    private void process(SelectionKey key) throws Exception {
        //针对每一种状态给一个反应
        if (key.isAcceptable()) {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            //当数据准备就绪的时候,将状态改为可读
            channel.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
            // key.channel 从多路复用器中拿到客户端的引用
            SocketChannel channel = (SocketChannel) key.channel();
            int len = channel.read(buffer);
            if (len > 0) {
                buffer.flip();
                String content = new String(buffer.array(), 0, len);
                key = channel.register(selector, SelectionKey.OP_WRITE);
                //在key上携带一个附件,一会再写出去
                key.attach(content);
                System.out.println("读取内容:" + content);
            }
        } else if (key.isWritable()) {
            SocketChannel channel = (SocketChannel) key.channel();
            String content = (String) key.attachment();

            channel.write(ByteBuffer.wrap(("输出: " + content).getBytes()));
            channel.close();
        }
    }

    public static void main(String[] args) {
        new NIOServer(8080, false).listen();
    }
}

