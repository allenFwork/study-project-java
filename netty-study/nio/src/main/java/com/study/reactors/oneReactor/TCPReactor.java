package com.study.reactors.oneReactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

// Reactor线程（单线程）
public class TCPReactor implements Runnable {

    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;

    public TCPReactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress addr = new InetSocketAddress(port);
        // 在ServerSocketChannel绑定监听端口
        serverSocketChannel.socket().bind(addr);
        // 设置ServerSocketChannel为非阻塞
        serverSocketChannel.configureBlocking(false);
        // ServerSocketChannel向selector注册一個 OP_ACCEPT 事件，然后返回该通道的key
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 給定key一個附加的Acceptor对象，这里附加了 Acceptor 对象
        selectionKey.attach(new Acceptor(selector, serverSocketChannel));
    }

    @Override
    public void run() {
        // 在线程被中断前持续运行，相当于while(true)
        while (!Thread.interrupted()) {
            System.out.println("Waiting for new event on port: " + serverSocketChannel.socket().getLocalPort() + " ... ");
            try {
                // 若沒有事件就緒,则不往下执行
                if (selector.select() == 0)
                    continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取得所有已就緒事件的key集合
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();
            while (it.hasNext()) {
                // 根據事件的key進行调度
                dispatch((it.next()));
                it.remove();
            }
        }
    }

    /*
     * name: dispatch(SelectionKey key)
     * description: 調度方法，根據事件綁定的對象開新線程
     */
    private void dispatch(SelectionKey key) {
        // 根據事件之key綁定的對象调用方法，取出附加对象：Acceptor对象
        Runnable r = (Runnable) (key.attachment());
        if (r != null)
            r.run();
    }

}