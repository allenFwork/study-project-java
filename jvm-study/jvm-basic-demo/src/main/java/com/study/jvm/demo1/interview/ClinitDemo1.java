package com.study.jvm.demo1.interview;

/**
 * 初始化阶段相关面试题
 */
public class ClinitDemo1 {
    public static void main(String[] args) {
        System.out.println("A");
        new ClinitDemo1();
        new ClinitDemo1();
        // 打印结果(预期)：D A B C B C
        // 打印结果(实际)：D A C B C B
    }

    public ClinitDemo1() {
        System.out.println("B");
    }

    {
        System.out.println("C");
    }

    static {
        System.out.println("D");
    }
}

