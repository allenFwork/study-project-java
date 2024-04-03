package com.study.jvm.reference;

/**
 * 引用计数
 * 启动时，添加VM参数：-verbose:gc，打印对应垃圾回收日志
 * 发现打印的日志中，gc回收完，年轻代中的占用内存数量级没有变化，没有发生内存泄漏，所以a1和b1一定都被回收了
 */
public class ReferenceCountingDemo {
    public static void main(String[] args) {
        while (true) {
            A a1 = new A();
            B b1 = new B();
            a1.b = b1;
            b1.a = a1;
            a1 = null;
            b1 = null;
            System.gc();
        }
    }
}

class A {
    B b;
}

class B {
    A a;
}