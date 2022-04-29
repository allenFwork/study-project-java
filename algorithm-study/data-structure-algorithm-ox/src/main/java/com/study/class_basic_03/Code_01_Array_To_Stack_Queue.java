package com.study.class_basic_03;

/**
 * 从固定数组实现栈
 * 从固定数组实现队列
 */
public class Code_01_Array_To_Stack_Queue {

	/**
	 * 通过数组实现的栈结构
	 */
	public static class ArrayStack {
		private Integer[] arr;
		private Integer index;

		// 初始化设置栈的大小
		public ArrayStack(int initSize) {
			if (initSize < 0) {
				throw new IllegalArgumentException("The init size is less than 0");
			}
			arr = new Integer[initSize];
			index = 0;
		}

		// 查询栈顶的数
		public Integer peek() {
			if (index == 0) {
				return null;
			}
			return arr[index - 1];
		}

		// 加一个数到栈中
		public void push(int obj) {
			if (index == arr.length) {
				throw new ArrayIndexOutOfBoundsException("The queue is full");
			}
			arr[index++] = obj;
		}

		// 从栈中取出一个数到(从栈顶弹出来一个数)
		public Integer pop() {
			if (index == 0) {
				throw new ArrayIndexOutOfBoundsException("The queue is empty");
			}
			return arr[--index];
		}
	}

	/**
	 * 通过数组实现队列结构
	 */
	public static class ArrayQueue {
		private Integer[] arr;
		// 约束,判断此时队列的存放情况,存放了多少个元素
		private Integer size;
		// 队列（数组）中第一个元素的索引
		private Integer firstIndex;
		// 队列（数组）中最后一个元素的索引
		private Integer lastIndex;

		// 初始化对列,设置队列的大小
		public ArrayQueue(int initSize) {
			if (initSize < 0) {
				throw new IllegalArgumentException("The init size is less than 0");
			}
			// 队列的真是大小,即数组的大小
			arr = new Integer[initSize];
			// 此时队列中元素的数量
			size = 0;
			firstIndex = 0;
			lastIndex = 0;
		}

		public Integer peek() {
			if (size == 0) {
				return null;
			}
			return arr[firstIndex];
		}

		// 像队列中添加元素
		public void push(int obj) {
			if (size == arr.length) {
				throw new ArrayIndexOutOfBoundsException("The queue is full");
			}
			size++;
			arr[lastIndex] = obj;
			// 如果添加完元素到队列中后,最后一个元素的索引指向了数组的最后一位,就将该索引执行数组第一位
			lastIndex = lastIndex == arr.length - 1 ? 0 : lastIndex + 1;
		}

		// 去除队列中的元素,即取出队列第一个元素(最先进去的元素)
		public Integer poll() {
			if (size == 0) {
				throw new ArrayIndexOutOfBoundsException("The queue is empty");
			}
			size--;
			int tmp = firstIndex;
			// 如果取出元素后,下一个取出元素的索引指向了数组的最后一位,就将该索引执行数组第一位
			firstIndex = firstIndex == arr.length - 1 ? 0 : firstIndex + 1;
			return arr[tmp];
		}
	}

	public static void main(String[] args) {

	}

}
