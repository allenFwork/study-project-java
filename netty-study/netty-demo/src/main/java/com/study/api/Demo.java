package com.study.api;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

/**
 * netty 中的 buffer：io.netty.buffer.ByteBuf
 * 相关API操作
 */
public class Demo {

    public static void main(String[] args) {
        // 1. 创建一个非池化的 ByteBuffer，大小为 10 个字节(netty的buffer)
        ByteBuf byteBuf = Unpooled.buffer(10);
        System.out.println("原始byteBuf为：" + byteBuf.toString());
        System.out.println("byteBuf中的内容为：" + Arrays.toString(byteBuf.array()));
        /* 结果：
         * 原始byteBuf为：UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 0, cap: 10)
         * byteBuf中的内容为：[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         */

        // 2. 写入一段内容
        byte[] bytes = {1, 2, 3, 4, 5};
        byteBuf.writeBytes(bytes);
        System.out.println("写入的bytes为：" + Arrays.toString(bytes));
        System.out.println("写入一段内容后byteBuf为：" + byteBuf.toString());
        System.out.println("byteBuf中的内容为：" + Arrays.toString(byteBuf.array()));
        /*
         * 结果：
         * 写入的bytes为：[1, 2, 3, 4, 5]
         * 写入一段内容后byteBuf为：UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 5, cap: 10)
         * byteBuf中的内容为：[1, 2, 3, 4, 5, 0, 0, 0, 0, 0]
         */

        // 3. 读取一段内容
        byte byte1 = byteBuf.readByte(); // 调用一次，读一个字节
        byte byte2 = byteBuf.readByte(); // 调用一次，读一个字节
        System.out.println("读取的bytes为：" + Arrays.toString(new byte[]{byte1, byte2}));
        System.out.println("读取一段内容后byteBuf为：" + byteBuf.toString());
        System.out.println("byteBuf中的内容为：" + Arrays.toString(byteBuf.array())); // 读取是底层的数据，与指针没有关系
        /* 结果：
         * 读取的bytes为：[1, 2]
         * 读取一段内容后byteBuf为：UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 2, widx: 5, cap: 10)
         * byteBuf中的内容为：[1, 2, 3, 4, 5, 0, 0, 0, 0, 0]
         */

        // 4. 将读取的内容丢弃
        byteBuf.discardReadBytes(); // 底层数据向前移动，没有数据的位置不移动
        System.out.println("将读取的内容byteBuf为：" + byteBuf.toString());
        System.out.println("byteBuf中的内容为：" + Arrays.toString(byteBuf.array()));
        /* 结果：
         * 将读取的内容byteBuf为：UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 3, cap: 10)
         * byteBuf中的内容为：[3, 4, 5, 4, 5, 0, 0, 0, 0, 0]
         */

        // 5. 清空读写指针，只清空指针，不清空底层数据
        byteBuf.clear();
        System.out.println("将读写指针清空后byteBuf为" + byteBuf.toString());
        System.out.println("byteBuf中的内容为：" + Arrays.toString(byteBuf.array()) + "\n");

        // 6.再次写入一段内容, 比第一段内容少
        byte[] bytes2 = {1, 2, 3};
        byteBuf.writeBytes(bytes2);
        System.out.println("写入的bytes为：" + Arrays.toString(bytes2));
        System.out.println("写入一段内容后byteBuf为：" + byteBuf.toString());
        System.out.println("byteBuf中的内容为：" + Arrays.toString(byteBuf.array()) + "\n");
        /* 结果：
         * 写入的bytes为：[1, 2, 3]
         * 写入一段内容后byteBuf为：UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 3, cap: 10)
         * byteBuf中的内容为：[1, 2, 3, 4, 5, 0, 0, 0, 0, 0]
         */

        // 7. 将byteBuf清零（内容上的清空）
        byteBuf.setZero(0, byteBuf.capacity());
        System.out.println("将内容清零后的byteBuf为：" + byteBuf.toString());
        System.out.println("byteBuf中的内容为：" + Arrays.toString(byteBuf.array()) + "\n");
        /* 结果：
         * 将内容清零后的byteBuf为：UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 3, cap: 10)
         * byteBuf中的内容为：[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         */

        // 8. 再次写入一段超过容量的内容
        byte[] bytes3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        byteBuf.writeBytes(bytes3); // 进行了自动扩容
        System.out.println("写入的bytes3为：" + Arrays.toString(bytes3));
        System.out.println("写入一段内容后byteBuf为：" + byteBuf.toString());
        System.out.println("byteBuf中的内容为：" + Arrays.toString(byteBuf.array()));
        /*
         * 结果：
         * 写入的bytes3为：[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
         * 写入一段内容后byteBuf为：UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 14, cap: 64)
         * byteBuf中的内容为：[0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         */
    }

}
