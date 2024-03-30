package com.study.jvm.demo1.interview;

/**
 * 初始化阶段相关面试题
 * 不会执行Test2_A的初始化方法
 */
public class ClinitDemo2 {
    public static void main(String[] args) {
        Test2_A[] arr = new Test2_A[10];
    }
}
class Test2_A {
    static {
        System.out.println("静态代码块运行了，即 Test2_A clinit 方法执行了 ... ");
    }
}

