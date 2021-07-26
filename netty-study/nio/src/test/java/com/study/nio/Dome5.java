package com.study.nio;

import java.nio.ByteBuffer;

public class Dome5 {

    public static void main(String[] args) {
        // 创建大小为 100 的 byteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(100);
        // 将不是byte的数据加入到byte对应的buffer中，但是要注意放进去的类型
        buffer.putChar('a');
        buffer.putInt(2);
        buffer.putLong(50000L);
        buffer.putShort((short) 2);
        buffer.putDouble(12.4);
        System.out.println(buffer.position());
        buffer.flip();
        // 从 byteBuffer 中取出来对应的数据也要与存进去的类型对应
        System.out.println(buffer.getChar());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getDouble());
    }

}
