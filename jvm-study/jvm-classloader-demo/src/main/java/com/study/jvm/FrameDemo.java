package com.study.jvm;

public class FrameDemo {
    public static void main(String[] args) {
        recursion();
    }

    public static int count = 0;

    // 递归调用自己，测试虚拟机栈的大小
    public static void recursion() {
        System.out.println(++count);
        recursion();
    }
}
