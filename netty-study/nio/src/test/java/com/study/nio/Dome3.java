package com.study.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件输出流转为文件管道写入数据
 */
public class Dome3 {

    public static void main(String[] args) throws Exception {
        // 获取文件输出流：没有该文件，会生成该文件，默认覆盖操作
        FileOutputStream fileOutputStream = new FileOutputStream("netty-study/nio/src/test/resources/dome3.txt");
        FileChannel channel = fileOutputStream.getChannel();
        // 定义byte数组
        byte[] bytes = "superman".getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        // 读取byteBuffer中的数据，写入到文件管道中
        channel.write(byteBuffer);
        // 关闭文件输出流
        fileOutputStream.close();
    }

}
