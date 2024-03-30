package com.study.jvm.demo1.clinit;

/**
 * 测试是否执行过了类的初始化方法，即clinit方法
 * 1.变量是final修饰的，并且等号右边是常量不会触发初始化
 * 添加 -XX:+TraceClassLoading 作为 虚拟机参数打印加载的类
 */
public class Demo3 {
    public static void main(String[] args) {
        int i = Demo4.i;
        System.out.println(i);
    }
}

class Demo4 {
    static {
        System.out.println("初始化 Demo4 ，即执行了Demo4的clinit方法 ...");
    }

    public static int i = 0;
    // public static final int i = 0;
}
