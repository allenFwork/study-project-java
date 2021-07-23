package com.study.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * int类型的buffer：IntBuffer的使用（读写操作）
 */
public class Dome1 {

    public static void main(String[] args) {
        // int 类型的 buffer ： java.nio.IntBuffer
        // 底层是 HeapIntBuffer 对象，堆内缓冲
        IntBuffer buffer = IntBuffer.allocate(8); // 设置了容量capacity为8
        for (int i=0; i<buffer.capacity(); i++) {
            int nextInt = new SecureRandom().nextInt(20);
            // 向buffer中写入数据，写到最后buffer中写满了数据，此时position等于capacity，为8
            buffer.put(nextInt);
        }
        // flip方法 就是将position的值赋值给limit, position值变为0
        buffer.flip();
        // hasRemaining方法就是 position < limit 的判断，表示是否可读
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

    }

}
