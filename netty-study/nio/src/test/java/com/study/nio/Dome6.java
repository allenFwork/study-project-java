package com.study.nio;

import java.nio.ByteBuffer;

public class Dome6 {

    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        for(int i=0; i<byteBuffer.capacity(); ++i){
            byteBuffer.put((byte)i);
        }

//        // 第一步：保存标记(mark = position), mark等于-1表示未开启mark
//        byteBuffer.mark();
//        // ... 读取操作
//        // 第二步：回到原来的标记处(position = mark)
//        byteBuffer.reset();

        // 修改byteBuffer的position值为2
        byteBuffer.position(2);
        // 修改byteBuffer的limit值为8
        byteBuffer.limit(8);
        // slice()方法是基于此时 position 和 limit 的位置复制出来一个buffer,会影响到原来的buffer(数据共享)
        ByteBuffer resetBuffer = byteBuffer.slice();
        System.out.println("position：" + resetBuffer.position());
        System.out.println("limit：" + resetBuffer.limit());
        System.out.println("capacity：" + resetBuffer.capacity());

        // 遍历复制出来的resetBuffer，对其中每一个只进行乘2操作，然后放回原来的位置
        for(int i=0; i<resetBuffer.capacity(); i++){
            byte anInt = resetBuffer.get();
            resetBuffer.put(i, (byte) (anInt*2));
        }

        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());
        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }

    }
}
