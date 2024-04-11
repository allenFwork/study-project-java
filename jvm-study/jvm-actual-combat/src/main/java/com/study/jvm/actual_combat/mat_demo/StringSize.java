package com.study.jvm.actual_combat.mat_demo;

import org.openjdk.jol.info.ClassLayout;

public class StringSize {
    public static void main(String[] args) {
        // 使用JOL框架打印String对象
        // 1.parseClass方法解析对象的组成
        // 2.toPrintable方法打印组成信息
        System.out.print(ClassLayout.parseClass(String.class).toPrintable());
    }
}
