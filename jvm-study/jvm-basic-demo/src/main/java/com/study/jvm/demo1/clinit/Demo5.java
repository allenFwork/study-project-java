package com.study.jvm.demo1.clinit;

/**
 * 测试是否执行过了类的初始化方法，即clinit方法
 * 2.调用Class.forName(String className)
 * 添加 -XX:+TraceClassLoading 作为 虚拟机参数打印加载的类
 */
public class Demo5 {
    public static void main(String[] args) {
        try {
            Class.forName("com.study.jvm.demo1.clinit.Demo6");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

class Demo6 {
    static {
        System.out.println("初始化 Demo6 ，即执行了 Demo6 的clinit方法 ...");
    }
}
