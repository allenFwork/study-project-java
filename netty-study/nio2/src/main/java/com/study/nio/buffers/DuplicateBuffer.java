package com.study.nio.buffers;

import java.nio.Buffer;
import java.nio.CharBuffer;

/**
 * buffer复制：duplicate方法的使用
 * 浅复制-底层数据只有一份
 */
public class DuplicateBuffer {

	public static void main(String[] args) {
		CharBuffer buffer = CharBuffer.allocate(8);
		for(int i= 0 ; i < buffer.capacity() ; i++) {
			buffer.put(String.valueOf(i).charAt(0));
		}
		// 因为还是写模式(position=limit的值)，所以读取出来的数据为空
		printBuffer(buffer); // [limit=8, position = 8, capacity = 8, array = ]

		// 写入完成后，进行翻转
		buffer.flip();
		printBuffer(buffer);

		// 直接进行设置buffer的position、limit
 		buffer.position(3).limit(6).mark().position(5);
 		printBuffer(buffer); // [limit=6, position = 5, capacity = 8, array = 5]

		CharBuffer dupeBuffer = buffer.duplicate();
		buffer.clear();
 		printBuffer(buffer);
 		
		printBuffer(dupeBuffer); // [limit=6, position = 5, capacity = 8, array = 5]
		
 		dupeBuffer.clear();
		printBuffer(dupeBuffer); // [limit=8, position = 0, capacity = 8, array = 01234567]
		
	}
	
	private static void printBuffer(Buffer buffer) {
		System.out.println("[limit=" + buffer.limit() 
							+", position = " + buffer.position()
							+", capacity = " + buffer.capacity()
							+", array = " + buffer.toString()+"]");
	}

}
