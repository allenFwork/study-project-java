package com.study.io.basic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CharStreamDemo2 {

    public static void basicUse() throws IOException {
        // 使用文件名称创建流对象
        FileWriter fw = new FileWriter("D:\\documents\\study\\test\\3.txt");

        // 写出数据
        fw.write(97);  // 写出第1个字符
        fw.write('b'); // 写出第2个字符
        fw.write('d'); // 写出第3个字符
        fw.write(30000);// 写出第4个字符，中文编码表中30000对应一个汉字。

        // 【注意】关闭资源时,与FileOutputStream不同。如果不关闭,数据只是保存到缓冲区，并未保存到文件。
        fw.close();
    }

    public static void basicUse2() throws IOException {
        // 使用文件名称创建流对象(追加内容，不清空原文件)
        FileWriter fw = new FileWriter("D:\\documents\\study\\test\\3.txt", true);

        // 写出数据
        fw.write('刷');  // 写出第1个字符
        fw.write('新'); // 写出第2个字符
        // 将缓存区的数据刷新，会将缓存中的数据写入到文件中，然后清空缓存区
        fw.flush();

        fw.write('关'); // 写出第1个字符
        fw.write('闭'); // 写出第2个字符
        fw.flush();

        // 【注意】关闭资源时,与FileOutputStream不同。如果不关闭,数据只是保存到缓冲区，并未保存到文件。
        fw.close();
    }

    public static void basicUse3() throws IOException {
        // 使用文件名称创建流对象(追加内容，不清空原文件)
        File file = new File("D:\\documents\\study\\test\\3.txt");
        FileWriter fw = new FileWriter(file);

        // 字符串转换为字节数组
        char[] charArr = "我是超人，正在归来".toCharArray();

        // 写出字符数组
        fw.write(charArr);
        fw.flush();

        // 写出从索引2开始，7个字节。索引2是'超'，7个字节，也就是'超人，正在归来'。
        fw.write(charArr, 2, 7); // 数组下表越界，会报 java.lang.IndexOutOfBoundsException 错误
        fw.flush();

        // 关闭资源时
        fw.close();
    }

    public static void basicUse4() throws IOException {
        // 使用文件名称创建流对象
        FileWriter fw = new FileWriter("D:\\documents\\study\\test\\3.txt");

        // 写出字符串
        fw.write("我是超人，正在归来");
        // fw.flush(); // 此处可以刷新，也可以不刷新
        fw.write("我是超人，正在归来", 2, 7);
        fw.flush();

        // 关闭资源时
        fw.close();
    }

    public static void specialChar() throws IOException {
        // 使用文件名称创建流对象
        FileWriter fw = new FileWriter("D:\\documents\\study\\test\\3.txt");

        // 写出字符串
        fw.write("我是超人，正在归来");
        // 写出换行
        fw.write("\r\t");
//        fw.write("\t"); // \t 表示空一个制表符（windows系统中测试）
//        fw.write("\r"); // \r 表示换行（windows系统中测试）
        // 写出字符串
        fw.write("我已经归来了");
        fw.flush();

        // 关闭资源时
        fw.close();
    }


    public static void main(String[] args) throws IOException {
//        basicUse();
//        basicUse2();
//        basicUse3();
//        basicUse4();
        specialChar();
    }

}
