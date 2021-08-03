package com.study.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统的BIO的聊天室服务端
 */
public class BioServer {

    ServerSocket serverSocket;

//    public BioServer() {
//        try {
//            // 聊天室服务器的端口号,服务器端的socket
//            serverSocket = new ServerSocket(9999);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 服务器端启动
//    public void start() {
//        // 一直等待连接
//        while (true) {
//            try {
//                Socket socket = serverSocket.accept(); // 此时代码是阻塞的
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        new BioServer().start();
//    }

    public static void main(String[] args) {
        // 服务器端的socket
        ServerSocket serverSocket = null;
        try {
            // 设置聊天室服务器的端口号
            serverSocket = new ServerSocket(9999);
            TimeServerHandlerExecutorPool timeServerHandlerExecutorPool =
                    new TimeServerHandlerExecutorPool(50, 1000);
            // 一直等待连接
            while (true) {
                Socket socket = serverSocket.accept();  //阻塞
                System.out.println("客户端" + socket.getRemoteSocketAddress().toString() + "来连接了");
//                 socket.getInputStream().read(); // 阻塞
//                new Thread(new BioServerHandler(socket)).start();
                timeServerHandlerExecutorPool.execute(new BioServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
