package com.study.io.buffer;

import java.io.*;

/**
 * 字节缓冲流的学习：2种进本字节流的增强
 */
public class BufferStreamDemo {

    // 使用基本的字节流处理
    public static void byteStreamTest() {
        // 记录开始时间
        long start = System.currentTimeMillis();
        // 创建字节流流对象
        try (FileInputStream fis = new FileInputStream("D:\\documents\\study\\test\\test.mp4");
             FileOutputStream fos = new FileOutputStream("D:\\documents\\study\\test\\test2.mp4")) {
            // 读写数据
            int data;
            while ((data = fis.read()) != -1) // 注意这里是 while，不是 if
                fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 记录结束时间
        long end = System.currentTimeMillis();
        System.out.println("普通字节流复制时间:" + (end - start) + " 毫秒");
    }

    // 使用缓冲字节流处理
    public static void bufferedStreamTest() {
        // 记录开始时间
        long start = System.currentTimeMillis();

        // 创建缓冲字节流流对象
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("D:\\documents\\study\\test\\test.mp4"));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("D:\\documents\\study\\test\\test3.mp4"))
        ) {
            // 读写数据
            int data;
            while ((data = bis.read()) != -1) {
                bos.write(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 记录结束时间
        long end = System.currentTimeMillis();
        System.out.println("缓冲字节流复制时间:" + (end - start) + " 毫秒");
    }

    // 使用缓冲字节流处理(改进版本)
    public static void bufferedStreamUpdateTest() {
        // 记录开始时间
        long start = System.currentTimeMillis();

        // 创建缓冲字节流流对象
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("D:\\documents\\study\\test\\test.mp4"));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("D:\\documents\\study\\test\\test3.mp4"))
        ) {
            // 读写数据
            int len;
            byte[] buffer = new byte[8 * 1024];
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 记录结束时间
        long end = System.currentTimeMillis();
        System.out.println("缓冲字节流复制时间:" + (end - start) + " 毫秒");
    }

    public static void main(String[] args) {
//        byteStreamTest();
//        bufferedStreamTest();     // 缓冲字节流复制时间:9572 毫秒
        bufferedStreamUpdateTest(); // 缓冲字节流复制时间:1193 毫秒
    }
}
