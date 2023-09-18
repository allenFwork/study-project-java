package com.study.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * IO学习：字节流 综合练习
 */
public class ByteStreamDemo3 {

    // 复制文件
    public static void copyFile(String sourceFilePath, String resultFilePath) throws IOException {
        // 1.创建流对象
        // 1.1 指定数据源
        FileInputStream fis = new FileInputStream(sourceFilePath);
        // 1.2 指定目的地
        FileOutputStream fos = new FileOutputStream(resultFilePath);

        // 2.读写数据
        // 2.1 定义数组
        byte[] buffer = new byte[1024];
        // 2.2 定义长度
        int len;
        // 2.3 循环读取
        while ((len = fis.read(buffer)) != -1) {
            // 2.4 写出数据
            fos.write(buffer, 0, len);
        }

        // 3.关闭资源
        fos.close();
        fis.close();
    }

    public static void main(String[] args) throws IOException {
        copyFile("D:\\documents\\study\\test\\1_io.jpg", "D:\\documents\\study\\test\\1_io_copy.jpg");
    }

}
