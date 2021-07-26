package com.study.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BioServerHandler implements Runnable {

    // 负责客户端通信
    private Socket socket;

    public BioServerHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // 获取输入流：客户端发来的数据
            inputStream = socket.getInputStream();
            // 获取输出流：给客户端发送的数据
            outputStream = socket.getOutputStream();
            int count = 0;
            String content = null;
            // 字节数组存放数据
            byte[] bytes = new byte[1024];
            // inputStream.read(bytes)：从inputStream流中读取数据，写入到bytes数组中，返回从流里面读取了多少个字节数据
            while ((count = inputStream.read(bytes))!=-1) {
                // 从 bytes字节数组中读取数据，从数组第一位开始，读取 count个字节，将字节数组转化为字符串，编码格式为utf-8
                String line = new String(bytes,0, count,"utf-8");
                System.out.println(line);
                // 判断客户端发过来的是不是 “sj” 时间字符串，是返回具体时间给客户端
                content = line.trim().equalsIgnoreCase("SJ") ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) : "你发的啥？";
                // 将返回给客户端的字符串转化为字节数组，并将字节数组写入到输出流中（放到了缓冲区）
                outputStream.write(content.getBytes());
                // 通过输出流的 flush方法，将缓冲区中的数据发送给客户端
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
