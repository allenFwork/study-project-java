package com.study.io.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * IO学习：字节流 InputStream 输入流
 */
public class ByteStreamDemo2 {

    public static void basicUse() throws FileNotFoundException {
        // 创建 FileInputStream对象 方法一
        FileInputStream fis = new FileInputStream("D:\\documents\\study\\test\\2.txt");

        // 创建 FileInputStream对象 方法二
        File file = new File("D:\\documents\\study\\test\\2.txt");
        fis = new FileInputStream(file);
    }

    public static void basicUse2() throws IOException {
        // 使用文件名创建流对象
        FileInputStream fis = new FileInputStream("D:\\documents\\study\\test\\2.txt");
        // 读取数据，返回一个字节
        int read = fis.read();
        // 将读取出来的数据，强转位字符类型，并进行打印（文件中就是字符数据，读的时候是二进制流，每次读一个字节，读成了int类型）
        System.out.println((char) read);
        read = fis.read();
        System.out.println((char) read);
        read = fis.read();
        System.out.println((char) read);
        read = fis.read();
        System.out.println((char) read);
        read = fis.read();
        System.out.println((char) read);
        read = fis.read();
        System.out.println((char) read);
        read = fis.read();
        System.out.println((char) read);
        read = fis.read();
        System.out.println((char) read);
        read = fis.read();
        System.out.println((char) read);
        // 读取到末尾,返回-1
        read = fis.read();
        System.out.println(read);
        // 关闭资源
        fis.close();
    }

    public static void basicUse3() throws IOException {
        // 使用文件名称创建流对象
        FileInputStream fis = new FileInputStream("D:\\documents\\study\\test\\2.txt");

        // 定义变量，保存数据
        int read;
        // 循环读取
        while ((read = fis.read()) != -1)
            System.out.println((char) read);

        // 关闭资源
        fis.close();
    }

    public static void basicUse4() throws IOException {
        // 使用文件名称创建流对象
        FileInputStream fis = new FileInputStream("D:\\documents\\study\\test\\2.txt");
        // 定义变量，作为有效个数
        int len;
        // 定义字节数组，作为装字节数据的容器
        byte[] buffer = new byte[2];
        // 循环读取
        while ((len = fis.read(buffer)) != -1) {
            // 每次读取后,把数组变成字符串打印
//            System.out.println(new String(buffer)); // 该方法存在问题：buffer数组中可能有无效数据
            System.out.println(new String(buffer, 0, len)); // 改进方案
        }
        // 关闭资源
        fis.close();
    }


    public static void main(String[] args) throws IOException {
//        basicUse();
//        basicUse2();
//        basicUse3();
        basicUse4();
    }

}
