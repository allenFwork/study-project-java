package com.study.nio.channels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件通道演示-文件复制
 */
public class CopyFile {

    static public void main(String args[]) throws Exception {

        // 子模块中使用相对路径，文件路径前面要加上子模块的名字
        String infile = "netty-study/nio2/src/main/resources/CopyFile.java";
        String outfile = "netty-study/nio2/src/main/resources/CopyFile.java.copy";

        // 文件流
        FileInputStream fileInputStream = new FileInputStream(infile);
        FileOutputStream fileOutputStream = new FileOutputStream(outfile);

        // 从流中获取通道
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            // 读入之前要清空
            buffer.clear();

            // 从输入Channel渠道中读取数据并写入到buffer中,返回读取了多少个字节, position自动前进
            int count = fileInputStreamChannel.read(buffer);

            // 读取不到数据,就结束循环读
            if (count == -1) {
                break;
            }

            // position = 0; limit=读到的字节数
            buffer.flip();

            // 从 buffer 中读数据,并写入到输出Channel渠道中
            fileOutputStreamChannel.write(buffer);
        }
    }
}
