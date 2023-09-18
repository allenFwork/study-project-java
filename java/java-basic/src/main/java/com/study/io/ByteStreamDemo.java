package com.study.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * IO学习：字节流 OutputStream输出流
 */
public class ByteStreamDemo {

    // 利用字节输出流将数据写入到文件中(清空文件原数据)
    public static void basicUse() throws IOException {
        /**
         * 1.创建字节流对象：FileOutputStream是OutputStream的子类，java.io.OutputStream是所有字节输出流的父类
         *   细节1：参数可以是字符串表示的路径，也可以是File对象
         *   细节2：如果文件是不存在的，那么就会在硬盘上创建一个新的文件，但是要保证父级路径是存在的，否则会报错
         *   细节3：如果文件已经存在，则会清空文件
         */
        FileOutputStream fos = new FileOutputStream("D:\\documents\\study\\test\\io\\1.txt");
        /**
         * 2.写出数据
         *   细节：write方法的参数是整数，但是实际上写到本地文件中的是整数在ASCII码中对应的字符
         */
        fos.write(97);
        fos.write('9');
        fos.write('7');

        fos.write(258);
        fos.write(3);
        fos.write(1);

        // 3.释放资源：每次使用完流之后都要释放资源，如果不释放资源，那么该文件会被JVM一致占用着
        fos.close();
    }

    // 利用字节输出流将数据写入到文件中(清空文件原数据)
    public static void basicUse2() throws IOException {
        // 1.创建对象
        FileOutputStream fos = new FileOutputStream("D:\\documents\\study\\test\\io\\1.txt");
        byte[] bytes = "学习IO的字节流操作".getBytes();
        // 2.写出数据
        fos.write(bytes);
        // 3.释放资源
        fos.close();
    }

    // 利用字节输出流将数据写入到文件中(清空文件原数据)
    public static void basicUse3() throws IOException {
        // 1.创建对象
        FileOutputStream fos = new FileOutputStream("D:\\documents\\study\\test\\io\\1.txt");
        byte[] bytes = "hello world".getBytes();
        // 2.写出数据：从索引2开始，5个字节长度。索引1是e，两个字节
        fos.write(bytes, 1, 5);
        // 3.释放资源
        fos.close();
    }

    // 利用字节输出流将数据写入到文件中(保留文件原数据)
    public static void basicUse4() throws IOException {
        // 1.创建对象
        FileOutputStream fos = new FileOutputStream("D:\\documents\\study\\test\\io\\1.txt", true);
        byte[] bytes = "hello world".getBytes();
        // 2.写出数据：从索引2开始，5个字节长度。索引1是e，两个字节
        fos.write(bytes, 1, 5);
        // 3.释放资源
        fos.close();
    }

    // 利用字节输出流将数据写入到文件中(保留文件原数据)
    public static void basicUse5() throws IOException {
        // 1.创建对象
        File file = new File("D:\\documents\\study\\test\\io\\1.txt");
        FileOutputStream fos = new FileOutputStream(file, true);
        // 字符串转换为字节数组
        byte[] bytes = "hello world".getBytes();
        // 2.写出数据：从索引2开始，5个字节长度。索引1是e，两个字节
        fos.write(bytes, 1, 5);
        // 3.释放资源
        fos.close();
    }

    // 特殊符号：换行符、回车符
    public static void specialChar() throws IOException {
        FileOutputStream fos = new FileOutputStream("D:\\documents\\study\\test\\1.txt", true);
        fos.write("=================================".getBytes());
        byte[] buffer = {97, 98, 99, 100, 101};
        for (byte b : buffer) {
            // 写一个字节
            fos.write(b);
            fos.write("\r\n".getBytes());
            //fos.write("\r".getBytes());//回车符号
            //fos.write("\n".getBytes());//换行符
        }
        // 关闭资源
        fos.close();
    }


    // 打印
    public static void binarySystem(int param) {
        // i得是从31开始，从最低位置开始比较，否则输出的时候二进制被倒过来了
        for (int i = 31; i >= 0; i--) {
            // 1 << i：表示1左移i位，等于2的i次方
            System.out.print(((1 << i) & param) == 0 ? "0" : "1");
            if (i % 8 == 0)
                System.out.print(" ");
        }
    }

    public static void main(String[] args) throws IOException {
        //basicUse();
        //basicUse2();
        //basicUse3();
        //basicUse4();
        basicUse5();
//        binarySystem(258);
        specialChar();
    }

}
