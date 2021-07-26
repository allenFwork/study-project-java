package com.study.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 通过nio实现聊天室的客户端
 */
public class ChatService {

    private Selector selector;

    /*
     * 1. 服务端的ServerSocketChannel,用来连接客户端的SocketChannel;
     * 2. 服务端连接上了客户端的socketChannel后（也就是接收到了客户端连接请求）,
     *    会创建一个SocketChannel与客户端的SocketChannel进行连接通信。
     */
    private ServerSocketChannel serverSocketChannel;

    private long timeout = 2000;

    public ChatService() {
        try {
            // 服务端channel
            serverSocketChannel = ServerSocketChannel.open();

            // 选择器对象
            selector = Selector.open();

            // 绑定端口
            serverSocketChannel.bind(new InetSocketAddress(9090));

            // 设置非阻塞式
            serverSocketChannel.configureBlocking(false);

            // 把ServerSocketChannel注册给Selector
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 监听连接

            System.out.println("Nio版本的服务端准备就绪 ... ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        int count = 0;
        long start = System.nanoTime();
        // 干活
        while (true) {
//            // 监控客户端
//            if (selector.select(timeout) == 0) {
//                System.out.println("2秒内没有客户端来连接我");
//                continue;
//            }
            /*
             * timeout毫秒时间内发生的事件添加到集合中，
             * 例如2秒内由客户端向客户端发送连接请求，那么就会添加连接事件到selector中
             */
            selector.select(timeout);
//            // 不指定时间，就会形成阻塞，一直等待
//            selector.select();

            long end = System.nanoTime();
            if (end - start >= TimeUnit.MILLISECONDS.toNanos(timeout)) {
                count = 1;
            } else {
                count++;
            }

            if (count >= 10) {
                System.out.println("有可能发生空轮询" + count + "次");
                rebuildSelector();
                count = 0;
                // 不会阻塞，立马返回
                selector.selectNow();
                continue;
            }

            // 得到SelectionKey对象，判断是事件（就是timeout时间内添加进来的事件）
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {     // 连接事件
                    // 获取网络通道
                    SocketChannel accept = serverSocketChannel.accept();
                    // 设置非阻塞式
                    accept.configureBlocking(false);
                    // 连接上了  注册读取事件
                    accept.register(selector, SelectionKey.OP_READ);
                    System.out.println(accept.getRemoteAddress().toString() + "上线了 ... ");
                }
                if (selectionKey.isReadable()) {       // 读取客户端数据事件
                    // 读取客户端发来的数据
                    readClientData(selectionKey);
                }
                // 手动从当前集合将本次运行完的对象删除
                iterator.remove();
            }
        }
    }

    private void
    rebuildSelector() throws IOException {
        Selector newSelector = Selector.open();
        Selector oldSelect = selector;
        for (SelectionKey selectionKey : oldSelect.keys()) {
            int i = selectionKey.interestOps();
            selectionKey.cancel();
            selectionKey.channel().register(newSelector, i);
        }
        selector = newSelector;
        oldSelect.close();
    }

    // 读取客户端发来的数据
    private void readClientData(SelectionKey selectionKey) throws IOException {
        System.out.println("============ com.study.chat.ChatService.readClientData ============");
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 将数据从socketChannel中读取出来，并写入到 byteBuffer 中
        int read = socketChannel.read(byteBuffer);
        byteBuffer.flip();
        if (read > 0) {
            byte[] bytes = new byte[read];
            byteBuffer.get(bytes, 0, read);
            // 读取了数据  广播
            String content = new String(bytes, "utf-8");
            writeClientData(socketChannel, content);
        }
    }

    // 广播  将读取的数据群发
    private void writeClientData(SocketChannel socketChannel, String content) throws IOException {
        // 返回注册到selector上的所有事件
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            // 判断事件是否取消了
            if (key.isValid()) {
                SelectableChannel channel = key.channel();
                // 判断是不是连接上客户端的SocketChannel，排除掉服务端的ServerSocketChannel
                if (channel instanceof SocketChannel) {
                    SocketChannel socketChannel1 = (SocketChannel) channel;
                    // 判断不是此时发给服务端信息的 socketChannel
                    if (channel != socketChannel) {
                        ByteBuffer wrap = ByteBuffer.wrap(content.getBytes());
                        socketChannel1.write(wrap);
                    }
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
        new ChatService().start();
    }

}
