package com.study.jvm.actual_combat.mat_demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加虚拟机参数： -XX:+HeapDumpBeforeFullGC -XX:HeapDumpPath=D:/documents/temp/matTest.hprof
 * -XX:+HeapDumpBeforeFullGC的作用是：在程序准备发生Full GC时，那么会将此时堆内存的快照打印一下
 */
public class HeapDemo {
    public static void main(String[] args) {
        TestClass a1 = new TestClass();
        TestClass a2 = new TestClass();
        TestClass a3 = new TestClass();
        String s1 = "itheima1";
        String s2 = "itheima2";
        String s3 = "itheima3";

        a1.list.add(s1);
        a2.list.add(s1);
        a2.list.add(s2);
        a3.list.add(s3);

        // System.out.print(ClassLayout.parseClass(TestClass.class).toPrintable());
        s1 = null;
        s2 = null;
        s3 = null;
        System.gc();
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class TestClass {
    public List<String> list = new ArrayList<>(10);
}
