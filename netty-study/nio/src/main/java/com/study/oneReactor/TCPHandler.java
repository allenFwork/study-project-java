// Handler線程
package com.study.oneReactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class TCPHandler implements Runnable {

    int state;
    private final SelectionKey selectionKey;
    private final SocketChannel socketChannel;

    public TCPHandler(SelectionKey selectionKey, SocketChannel socketChannel) {
        this.selectionKey = selectionKey;
        this.socketChannel = socketChannel;
        // 初始状态设置为READING
        state = 0;
    }

    @Override
    public void run() {
        try {
            if (state == 0)
                read();  // 读取网络数据
            else
                send(); // 发送网络数据
        } catch (IOException e) {
            System.out.println("[Warning!] A client has been closed.");
            closeChannel();
        }
    }

    private void closeChannel() {
        try {
            selectionKey.cancel();
            socketChannel.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private synchronized void read() throws IOException {
        // non-blocking下不可用Readers，因为Readers不支援non-blocking
        byte[] arr = new byte[1024];
        ByteBuffer byteBuffer = ByteBuffer.wrap(arr);

        int numBytes = socketChannel.read(byteBuffer); // 讀取字符串
        if (numBytes == -1) {
            System.out.println("[Warning!] A client has been closed.");
            closeChannel();
            return;
        }
        String str = new String(arr); // 將讀取到的byte內容轉為字符串型態
        if ((str != null) && !str.equals(" ")) {
            process(str); // 逻辑处理
            System.out.println(socketChannel.socket().getRemoteSocketAddress().toString() + " > " + str);
            // 改变状态
            state = 1;
            // 通过key改变通道注册的事件
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            // 使一個阻塞的selector操作立即返回
            selectionKey.selector().wakeup();
        }
    }

    private void send() throws IOException {

        String str = "Your message has sent to "
                + socketChannel.socket().getLocalSocketAddress().toString() + "\r\n";
        ByteBuffer buf = ByteBuffer.wrap(str.getBytes()); // wrap自動把buf的position設為0，所以不需要再flip()

        while (buf.hasRemaining()) {
            socketChannel.write(buf); // 回传給client回应字符串，发送buf的position位置 到limit位置為止之間的內容
        }

        state = 0; // 改變狀態
        selectionKey.interestOps(SelectionKey.OP_READ); // 通过key改变通道注册的事件
        selectionKey.selector().wakeup(); // 使一個阻塞的selector操作立即返回
    }

    void process(String str) {
        // do process(decode, logically process, encode)..
        // ..
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}