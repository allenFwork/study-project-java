package com.study.jvm.reference.weak;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 弱引用案例 - 基本使用
 */
public class WeakReferenceDemo {
    public static void main(String[] args) throws IOException {

        byte[] bytes = new byte[1024 * 1024 * 100];
        WeakReference<byte[]> weakReference = new WeakReference<byte[]>(bytes);
        bytes = null;
        System.out.println(weakReference.get()); // 打印结果是有数据的

        // 执行一次垃圾回收
        System.gc();

        System.out.println(weakReference.get()); // 打印结果为null
    }
}
