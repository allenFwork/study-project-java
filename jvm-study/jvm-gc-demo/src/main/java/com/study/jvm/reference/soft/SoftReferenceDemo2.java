package com.study.jvm.reference.soft;

import java.io.IOException;
import java.lang.ref.SoftReference;

/**
 * 软引用案例2 - 基本使用
 * 启动时，设置最大堆内存：-Xmx200m
 * 设置堆空间最大为200M，实际该程序能分配的空间绝对少于200m，就能看出软引用指向的对象是否被回收了
 */
public class SoftReferenceDemo2 {
    public static void main(String[] args) throws IOException {

        byte[] bytes = new byte[1024 * 1024 * 100];
        SoftReference<byte[]> softReference = new SoftReference<byte[]>(bytes);
        // 将强引用释放掉，此时只有一个软引用指向该数组空间
        bytes = null;
        System.out.println(softReference.get());

        byte[] bytes2 = new byte[1024 * 1024 * 100];
        System.out.println(softReference.get()); // 打印结果是null，因为空间不足，软引用指向的对象被回收了

        // 再分配100M的堆内存时，由于限制了堆最大为200M，此时一定会报 Exception in thread "main" java.lang.OutOfMemoryError: Java heap space 错误
//        byte[] bytes3 = new byte[1024 * 1024 * 100];

        // 隐患：当软引用这个盒子中指向的对象已经被回收了，此时 softReference 这个强引用实质上没有任何意义了，但是它还是存在与内存中，所以需要将其置为null
        softReference = null;

//        System.gc();

//        System.in.read();
    }
}
