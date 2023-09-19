package com.study.io.basic;

import java.io.FileReader;
import java.io.IOException;

public class CharStreamDemo {

    public static void basicUse() throws IOException {
        // 使用文件名称创建流对象
        FileReader fr = new FileReader("D:\\documents\\study\\test\\3.txt");
        // 定义变量，保存数据
        int data;
        // 循环读取
        while ((data = fr.read()) != -1) {
            System.out.println((char) data);
        }
        // 关闭资源
        fr.close();
    }

    public static void basicUse2() throws IOException {
        // 使用文件名称创建流对象
        FileReader fr = new FileReader("D:\\documents\\study\\test\\3.txt");
        // 定义变量，保存有效字符个数
        int len;
        // 定义变量，作为装字符数据的容器 (此处容器大小为2个字符，所以表示每次读取两个字符)
        char[] buffer = new char[2];
        // 循环读取
        while ((len = fr.read(buffer)) != -1) {
//            System.out.println(new java.lang.String(buffer));
            // 改进版本
            System.out.println(new java.lang.String(buffer, 0, len));
        }
        // 关闭资源
        fr.close();
    }

    public static void main(String[] args) throws IOException {
        //basicUse();
        basicUse2();
    }

}
