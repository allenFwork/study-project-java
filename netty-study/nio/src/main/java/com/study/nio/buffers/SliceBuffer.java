package com.study.nio.buffers;

import java.nio.ByteBuffer;

public class SliceBuffer {

    static public void main(String args[]) throws Exception {

        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }

        buffer.position(3);
        buffer.limit(7);

        // sliceBuffer的值是buffer的4-7位上的数据，sliceBuffer的capacity是4
        ByteBuffer sliceBuffer = buffer.slice();

        // 修改 sliceBuffer 中的值，发现 buffer 对应位置的数值也发生改变
        for (int i = 0; i < sliceBuffer.capacity(); ++i) {
            byte b = sliceBuffer.get(i);
            b *= 11;
            sliceBuffer.put(i, b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.remaining() > 0) {
            System.out.println(buffer.get());
        }
    }
}
