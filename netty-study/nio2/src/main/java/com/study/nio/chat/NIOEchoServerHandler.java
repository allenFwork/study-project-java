package com.study.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOEchoServerHandler implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    // 停止标识
    private volatile boolean stop;
    // 计数器
    private int num = 0;

    public NIOEchoServerHandler(int port) {
        try {
            // Selector.open() 静态方法获取Selector对象
            selector = Selector.open();
            // ServerSocketChannel.open() 静态方法获取ServerSocketChannel
            serverSocketChannel = ServerSocketChannel.open();
            // 设置非阻塞
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            // serverSocketChannel对象对 接受连接事件 感兴趣,注册到Selector对象中
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器在端口[" + port + "]等待客户请求......");
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
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null)
                                key.channel().close();
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        // 多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
        if (selector != null)
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            // 处理新接入的请求消息
            if (key.isAcceptable()) {
                // 从此时的SelectionKey对象中, 拿出它绑定的channel, 这里就是服务器的ServerSocketChannel对象
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                // ServerSocketChannel接受连接后，常见对应的SocketChannel对象（非阻塞的）
                SocketChannel socketChannel = ssc.accept(); // Non blocking, never null
                socketChannel.configureBlocking(false);
                // 将对应客户端的连接的SocketChannel对象也注册到Selector对象中，同时绑定对应的事件(读取事件)
                SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                selectionKey.attach(num++);
            }
            if (key.isReadable()) {
                // 读取数据
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("来自客户端[" + key.attachment() + "]的输入： [" + body.trim() + "]！");

                    if (body.trim().equals("Quit")) {
                        System.out.println("关闭与客户端[" + key.attachment() + "]......");
                        key.cancel();
                        sc.close();
                    } else {
                        String response = "来自服务器端的响应：" + body;
                        doWrite(sc, response);
                    }

                } else if (readBytes < 0) {
                    // 对端链路关闭
                    key.cancel();
                    sc.close();
                } else {

                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }
}
