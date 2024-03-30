package com.study.jvm.demo1.interview;

/**
 * 初始化阶段相关面试题
 * 会执行Test3_A的初始化方法
 */
public class ClinitDemo3 {
    public static void main(String[] args) {
        System.out.println(Test3_A.a);
    }
}

class Test3_A {
    public static final int a = Integer.valueOf(1);

    static {
        System.out.println("静态代码块运行了，即 Test3_A clinit 方法执行了 ... ");
    }
}

