package com.study.jvm.direct;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 直接内存
 */
public class DirectDemo {
    public static int size = 1024 * 1024 * 100;// 100MB
    public static List<ByteBuffer> list = new ArrayList<>();
    public static int count = 0;

    public static void main(String[] args) throws InterruptedException, IOException {
        // 等待第一次输入后，开始向直接内存中放数据（等待arthas启动，监控该进程）
        System.in.read();
        while (true) {
            ByteBuffer directBuffer = ByteBuffer.allocateDirect(size);
            list.add(directBuffer);
            System.out.println(++count);
            Thread.sleep(5000);
        }
    }
}
