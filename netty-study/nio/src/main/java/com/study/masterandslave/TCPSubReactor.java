package com.study.masterandslave;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TCPSubReactor implements Runnable {

    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;
    private boolean restart = false;
    int num;

    public TCPSubReactor(Selector selector, ServerSocketChannel serverSocketChannel, int num) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
        this.num = num;
    }

    @Override
    public void run() {
        // 在线程被中断前持续运行
        while (!Thread.interrupted()) {
            //System.out.println("ID:" + num
            //      + " subReactor waiting for new event on port: "
            //      + ssc.socket().getLocalPort() + "...");
            System.out.println("waiting for restart");
            // 在线程被中斷前, 以及被指定重启前持续运行
            while (!Thread.interrupted() && !restart) {
                try {
                    if (selector.select() == 0)
                        // 若沒有事件就绪，则不往下执行
                        continue;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 取得所有已就绪事件的key集合（不仅仅是注册给selector，还需要出发到的事件，才是已就绪的）
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    // 根据事件的key进行调度
                    dispatch((SelectionKey) (iterator.next()));
                    iterator.remove();
                }
            }
        }
    }

    /*
     * name: dispatch(SelectionKey key) description: 調度方法，根据事件綁定的对象开新线程
     */
    private void dispatch(SelectionKey key) {
        // 根据事件之key綁定的对象重开线程
        Runnable r = (Runnable) (key.attachment());
        if (r != null)
            r.run();
    }

    public void setRestart(boolean restart) {
        this.restart = restart;
    }
}