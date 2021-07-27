package com.study.oneReactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

// 接受连接请求的线程
public class Acceptor implements Runnable {

    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;

    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            // 接受client连接请求
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println(socketChannel.socket().getRemoteSocketAddress().toString() + " is connected.");

            if (socketChannel != null) {
                // 设置为非阻塞
                socketChannel.configureBlocking(false);
                // SocketChannel 向 selector 注册一個 OP_READ 事件，然后返回该通道的key
                SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                // 使一個阻塞的selector操作立即返回（下一次阻塞时起作用，为了刷新selector）
                selector.wakeup();
                // 給定key一個附加的TCPHandler對象，和读事件绑定
                selectionKey.attach(new TCPHandler(selectionKey, socketChannel));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}