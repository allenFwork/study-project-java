package com.study.jvm.stack;

/**
 * 虚拟机栈
 */
public class FrameDemo {
    public static void main(String[] args) {
        recursion();
    }

    public static int count = 0;

    // 递归调用自己，测试虚拟机栈的大小, 报 Exception in thread "main" java.lang.StackOverflowError 错误
    public static void recursion() {
        // 一个方法下的变量参数个数也会影响该方法对应栈帧的大小
//        int a, b, c, d, e, f, g;
        System.out.println(++count);
        recursion();
    }
}
