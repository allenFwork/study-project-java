package com.study.jvm.gc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 垃圾回收器案例1: 分代回收算法，将堆内存划分为了新生代和老年代，进行回收
 * 1.-XX:+UseSerialGC -Xms60m -Xmn20m -Xmx60m -XX:SurvivorRatio=3 -XX:+PrintGCDetails
 *   使用SerialGC垃圾回收器，堆内存的最大和初始大小设置为60M，新生代大小设置为20M，即老年代大小为40M，伊甸园区和幸存区的比例为3，即伊甸园区大小12M，幸存者区为4M、4M，打印GC的详细信息
 *
 * 2.-XX:+UseParNewGC -XX:+UseConcMarkSweepGC  -XX:ConcGCThreads
 *
 * 3.-XX:+UseParallelGC  -XX:+UseParallelOldGC
 */

public class GcDemo0 {

    public static void main(String[] args) throws IOException {
        List<Object> list = new ArrayList<>();
        int count = 0;
        while (true) {
            System.in.read();
            System.out.println(++count);
            // 每次添加1m的数据
            list.add(new byte[1024 * 1024 * 1]);
        }
    }
}
