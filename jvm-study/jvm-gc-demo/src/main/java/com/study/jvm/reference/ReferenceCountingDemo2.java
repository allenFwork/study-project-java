package com.study.jvm.reference;

import java.io.IOException;

/**
 * 查看GC Root对象案例
 */
public class ReferenceCountingDemo2 {
    public static A a2 = null;

    public static void main(String[] args) throws IOException {
        A a1 = new A();
        B b1 = new B();
        a1.b = b1;
        b1.a = a1;
        a2 = a1;
        // 让程序阻塞在这里，方便arthas进入该进程，进行相关操作
        System.in.read();
    }
}