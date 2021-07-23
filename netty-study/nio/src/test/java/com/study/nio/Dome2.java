package com.study.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 将文件输入流转换为文件管道进行读取数据
 */
public class Dome2 {

    public static void main(String[] args) throws Exception {
        // 获取文件输入流
        FileInputStream fileInputStream = new FileInputStream("netty-study/nio/src/test/resources/dome2.txt");
        // 从文件流升级到文件管道 （从bio升级到nio）
        FileChannel channel = fileInputStream.getChannel();
        // 创建字节的buffer：java.nio.ByteBuffer（抽象类）
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024); // 初始容量1024个字节
        // 从管道中读取数据，然后将数据写入到 byteBuffer 中
        channel.read(byteBuffer);
        // 翻转
        byteBuffer.flip();

        // 一个一个的便利获取
//        while (byteBuffer.remaining() > 0) {
//            System.out.println((char) byteBuffer.get());
//        }

        // 直接返回底层的数组
        String content = new String(byteBuffer.array());
        System.out.println(content);

        // 关闭文件流
        fileInputStream.close();
    }

}
