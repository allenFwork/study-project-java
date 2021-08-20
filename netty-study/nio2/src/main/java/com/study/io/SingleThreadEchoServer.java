package com.study.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 单线程式服务端
 */
public class SingleThreadEchoServer {

    private int port;

    public SingleThreadEchoServer(int port) {
        this.port = port;
    }

    public void startServer() {
        ServerSocket serverSocket = null;
        int i = 0;
        System.out.println("服务器在端口[" + this.port + "]等待客户请求......");
        try {
            serverSocket = new ServerSocket(this.port);
            while (true) {
                Socket clientRequest = serverSocket.accept();
                handleRequest(clientRequest, i++);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void handleRequest(Socket clientSocket, int clientNo) {
        PrintStream outputStream = null;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputStream = new PrintStream(clientSocket.getOutputStream());
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                // 输入'Quit'退出
                if (inputLine.equals("Quit")) {
                    System.out.println("关闭与客户端[" + clientNo + "]......" + clientNo);
                    outputStream.close();
                    bufferedReader.close();
                    clientSocket.close();
                    break;
                } else {
                    System.out.println("来自客户端[" + clientNo + "]的输入： [" + inputLine + "]！");
                    outputStream.println("来自服务器端的响应：" + inputLine);
                }
            }
        } catch (IOException e) {
            System.out.println("Stream closed");
        }
    }

    public static void main(String[] args) throws IOException {
        new SingleThreadEchoServer(8080).startServer();
    }
}
