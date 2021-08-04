package com.study.nio.buffers;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * buffer的读取
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

		buffer.compact();
		printBuffer(buffer);

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
