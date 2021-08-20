package com.study.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 多线程版本的服务端
 */
public class MultiThreadedEchoServerV1 {

    private int port;

    public MultiThreadedEchoServerV1(int port) {
        this.port = port;
    }

    public void startServer() {
        ServerSocket serverSocket = null;
        int i = 0;
        System.out.println("服务器在端口[" + this.port + "]等待客户请求......");
        try {
            serverSocket = new ServerSocket(8080);
            while (true) {
                Socket clientRequest = serverSocket.accept();
                new Thread(new ThreadedServerHandler(clientRequest, i++)).start();
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) throws IOException {
        new MultiThreadedEchoServerV1(8080).startServer();
    }
}
