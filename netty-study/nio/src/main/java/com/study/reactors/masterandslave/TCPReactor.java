package com.study.reactors.masterandslave;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

// Reactor线程
public class TCPReactor implements Runnable {

    private final ServerSocketChannel serverSocketChannel;
    // mainReactor用的selector
    private final Selector selector;

    public TCPReactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        // 设置ServerSocketChannel为非阻塞
        serverSocketChannel.configureBlocking(false);

        Acceptor acceptor = new Acceptor(serverSocketChannel);

        // ServerSocketChannel向selector注冊一個OP_ACCEPT事件，然后返回该通道的key
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 給定key一個附加的Acceptor對象
        selectionKey.attach(acceptor);

        InetSocketAddress addr = new InetSocketAddress(port);
        // 在ServerSocketChannel绑定监听端口
        serverSocketChannel.socket().bind(addr);
    }

    @Override
    public void run() {
        // 在线程被中断前持续运行
        while (!Thread.interrupted()) {
            System.out.println("mainReactor waiting for new event on port: "
                    + serverSocketChannel.socket().getLocalPort() + "...");
            try {
                // 若沒有事件就绪，则不往下执行
                if (selector.select() == 0)
                    continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取得所有已就绪事件的key集合
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                // 根据事件的key继续调度
                dispatch((SelectionKey) (iterator.next()));
                iterator.remove();
            }
        }
    }

    /*
     * name: dispatch(SelectionKey key)
     * description: 調度方法，根據事件綁定的對象開新線程
     */
    private void dispatch(SelectionKey key) {
        // 根据事件之key绑定的对象开新线程
        Runnable r = (Runnable) (key.attachment());
        if (r != null)
            r.run();
    }

}