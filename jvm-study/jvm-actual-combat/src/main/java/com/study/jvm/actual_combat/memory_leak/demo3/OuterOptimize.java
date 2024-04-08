package com.study.jvm.actual_combat.memory_leak.demo3;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 内存泄露问题：内部类引用外部类
 * 解决办法：将Inner类改为静态类,引用外部类的变量也变为静态变量
 */
public class OuterOptimize {
    private byte[] bytes = new byte[1024 * 1024]; // 外部类持有数据：1M
    private static String name = "测试";

    static class Inner {
        private String name;

        public Inner() {
            // 内部类引用了外部类的成员
            this.name = OuterOptimize.name;
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
            inners.add(new Inner());
        }
    }
}
