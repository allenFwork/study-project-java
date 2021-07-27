package com.study.masterandslave;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// Handler线程
public class TCPHandler implements Runnable {

    private final SelectionKey selectionKey;
    private final SocketChannel socketChannel;
    private static final int THREAD_COUNTING = 10;
    // 线程池
    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(THREAD_COUNTING, THREAD_COUNTING, 10,
                                                                    TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    // 以状态模式实现Handler
    HandlerState state;

    public TCPHandler(SelectionKey selectionKey, SocketChannel socketChannel) {
        this.selectionKey = selectionKey;
        this.socketChannel = socketChannel;
        // 初始状态设定为READING
        state = new ReadState();
        // 设置线程池最大线程数
        pool.setMaximumPoolSize(32);
    }

    @Override
    public void run() {
        try {
            state.handle(this, selectionKey, socketChannel, pool);
        } catch (IOException e) {
            System.out.println("[Warning!] A client has been closed.");
            closeChannel();
        }
    }

    public void closeChannel() {
        try {
            selectionKey.cancel();
            socketChannel.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void setState(HandlerState state) {
        this.state = state;
    }
}