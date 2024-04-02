package com.study.jvm.demo1.constant_pool;

/**
 * 字节码中 常量池的学习：
 * 1.字符串常量存储在常量池中
 * 2.类型的描述也会存储在常量池中，如“java.lang.String”也会存储在常量池中
 * 3.方法的描述也会存储在常量池中
 * 。。。
 */
public class ConstantPoolTest {
    public static final String a1 = "我爱北京天安门";
    public static final String a2 = "我爱北京天安门";

    public static void main(String[] args) {
        ConstantPoolTest constantPoolTest = new ConstantPoolTest();
    }
}
