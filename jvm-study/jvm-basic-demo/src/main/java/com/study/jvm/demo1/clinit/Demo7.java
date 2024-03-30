package com.study.jvm.demo1.clinit;

/**
 * 测试是否执行过了类的初始化方法，即clinit方法
 * 3.new 对象
 * 4.执行Main方法的当前类
 * 添加 -XX:+TraceClassLoading 作为 虚拟机参数打印加载的类
 */
public class Demo7 {

    static {
        System.out.println("初始化 Demo7 ，即执行了 Demo7 的clinit方法 ...");
    }
    public static void main(String[] args) {
        new Demo8();
    }
}

class Demo8 {
    static {
        System.out.println("初始化 Demo8 ，即执行了 Demo8 的clinit方法 ...");
    }
}
