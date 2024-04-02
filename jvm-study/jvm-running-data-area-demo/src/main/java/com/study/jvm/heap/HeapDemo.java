package com.study.jvm.heap;

import java.util.ArrayList;

/**
 * 堆空间学习
 * 1.观看堆内存空间的变化情况：used total max (堆的三个大小)
 * 2.设置堆内存： -Xmx1g -Xms1g
 */
public class HeapDemo {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Object> objects = new ArrayList<>();
        while (true) {
            objects.add(new byte[1024 * 1024 * 100]); // 一次添加100M的数据
            // 用来观察内存的变化，防止内存中添加太快，来不及观察
            Thread.sleep(1000);
        }
    }
}
