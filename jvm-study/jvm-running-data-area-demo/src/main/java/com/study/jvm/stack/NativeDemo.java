package com.study.jvm.stack;

import java.io.FileOutputStream;

/**
 * 本地方法栈
 * JDK8环境下，测试本地方法栈和虚拟机栈是同一个栈空间
 *
 */
public class NativeDemo {
    public static void main(String[] args) {
        try {
            // E:\temp\123.txt 此路径文件是不存在的
            FileOutputStream fileOutputStream = new FileOutputStream("E:\\temp\\123.txt");
            fileOutputStream.write(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
