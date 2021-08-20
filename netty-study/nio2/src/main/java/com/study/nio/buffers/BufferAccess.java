package com.study.nio.buffers;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * buffer的访问
 */
public class BufferAccess {

	public static void main(String[] args) {

		ByteBuffer buffer = ByteBuffer.allocate(10);
		printBuffer(buffer);
		
		buffer.put((byte)'H').put((byte)'e').put((byte)'l').put((byte)'l').put((byte)'0');
		printBuffer(buffer);

		buffer.flip();
		printBuffer(buffer);

		// 取buffer
		System.out.println("" + (char) buffer.get() + (char) buffer.get());
		printBuffer(buffer);

		// 做标记
		buffer.mark();
		printBuffer(buffer);

		// 读取两个元素后，恢复到之前mark的位置处
		System.out.println("" + (char) buffer.get() + (char) buffer.get());
		printBuffer(buffer);

		// 回退到mark标记时的状态
		buffer.reset();
		// buffer.rewind();
		printBuffer(buffer);

		/**
		 *  compact()方法将模式切换为写模式，作用：
		 *   1. 将目前剩余没有读取完的数据,全部放到buffer中,从0开始放
		 *   2. 将目前的position的值 改为 放完未读数据的位置的最后一位 + 1
		 *   3. limit 改为 capacity的值
		 */
		buffer.compact();
		printBuffer(buffer);

		// 所有指针回到初始状态
		buffer.clear();
		printBuffer(buffer);

	}
	
	private static void printBuffer(Buffer buffer) {
		System.out.println("[limit=" + buffer.limit() 
							+", position = " + buffer.position()
							+", capacity = " + buffer.capacity()
							+", array = " + buffer.toString()+"]");
	}
}
