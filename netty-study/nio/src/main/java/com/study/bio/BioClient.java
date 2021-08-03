package com.study.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 传统的BIO的聊天室客户端
 */
public class BioClient {

    public static void main(String[] args) {
        // 聊天室客户端 socket
        Socket socket = null;
        OutputStream outputStream = null;
        try {
            // 创建客户端socket实例，设置将要连接的ip和端口号
            socket = new Socket("127.0.0.1", 9999);

            /*
             * 此处必须开启一个新的线程，进行读取服务端发来的数据信息，
             * 即 socket.getInputStream.read() 方法的执行
             * 上述read方法是阻塞的，不获取到服务端的数据流，会一直阻塞在那里，
             * 所以不开启新线程代码逻辑无法向下执行
             */
            new Thread(new BioClientHandler(socket)).start();  // 任务中循环读

            outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入要发送的消息：");
            while (true) {
                String s = scanner.nextLine();
                if (s.trim().equals("by")) {
                    break;
                }
                outputStream.write(s.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
