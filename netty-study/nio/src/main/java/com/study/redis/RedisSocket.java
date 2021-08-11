package com.study.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 模拟redis服务端
 */
public class RedisSocket {

    private Socket socket;
    // redis客户端的输入流
    private InputStream inputStream;
    // redis客户端的输出流
    private OutputStream outputStream;

    // 在构造器中初始化
    public RedisSocket(String ip, int port) {
        try {
            if (!isCon()) {
                socket = new Socket(ip, port);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 发送给客户端信息
    public void send(String str) {
        System.out.println(str);
        try {
            outputStream.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读客户端发送过来的数据信息
    public String read() {
        byte[] bytes = new byte[1024];
        int count = 0;
        try {
            count = inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(bytes, 0, count);
    }


    public boolean isCon() {
        return socket != null && !socket.isClosed() && socket.isConnected();
    }

    public void close() {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
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
