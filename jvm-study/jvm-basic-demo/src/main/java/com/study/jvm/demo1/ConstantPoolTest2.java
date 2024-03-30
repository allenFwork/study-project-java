package com.study.jvm.demo1;

/**
 * 字节码 中 常量池的学习：
 * 1.字符串常量存储在常量池中
 * 2.
 */
public class ConstantPoolTest2 {
    public static final String a1 = "abc";
    public static final String a2 = "abc";
    public static final String abc = "abc";

    public static void main(String[] args) {
        ConstantPoolTest2 constantPoolTest = new ConstantPoolTest2();
    }
}
