package com.study.jvm.actual_combat.memory_leak.demo3;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 内存泄露问题：内部类引用外部类情况1
 * 非静态的内部类默认会持有外部类，尽管代码上不再使用外部类，所以如果有地方引用了这个非静态内部类，会导致外部类也被引用，垃圾回收时无法回收这个外部类。
 */
public class Outer {
    private byte[] bytes = new byte[1024]; // 外部类持有数据：1KB
    private String name = "测试";

    class Inner {
        private String name;

        public Inner() {
            // 内部类引用了外部类的成员，外部类的变量是成员变量，内部类是通过
            this.name = Outer.this.name;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.in.read();
        int count = 0;
        ArrayList<Inner> inners = new ArrayList<>();

        while (true) {
            if (count++ % 100 == 0) {
                Thread.sleep(10);
            }
            inners.add(new Outer().new Inner());
        }
    }
}
