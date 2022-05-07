package com.study.class_basic_01;

import java.util.Arrays;

/**
 * 堆排序（非常重要）：
 * 1.先对数组进行大根堆的排序，就是通过heapInsert方法排序，此时数组第一个（对应大根堆的根）是整个数组最大的值，将其与数组最后一位（大根堆叶子节点最右边的一个）交换位置
 * 2.对数组的第一位至倒数第二位进行大根堆排序，实质就是通过heapify方法进行排序
 * 3.交换第一位与倒数第二位的位置
 * 4. ...
 */
public class Code_03_HeapSort {

	public static void heapSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
        /**
         * 遍历数组arr,依次将i位置上的数值加入到大根堆中,
         * 实质是数组中位置的交换(将该数组从0到i位置的数组抽象成大根堆)
         * 遍历完整个数组,这整个数组就是一个抽象的大根堆了
         */
		for (int i = 0; i < arr.length; i++) {
			heapInsert(arr, i);
		}
		int size = arr.length;
		// 交换第一位与大根堆对应数组的最后一位数值
		swap(arr, 0, --size);
		while (size > 0) {
			heapify(arr, 0, size);
			swap(arr, 0, --size);
		}
	}

	// 向大根堆中插入新的节点数据
	public static void heapInsert(int[] arr, int index) {
        /**
         * 将数组抽象成完全二叉树，通过节点索引(i)映射父节点位置,即对应的索引: (i - 1) / 2
         * arr[index] > arr[(index - 1) / 2] ： 表示当前节点位置的值大于父节点位置的值，就进行交换
         * (-1 / 2) 计算机中计算结果是 0
         */
		while (arr[index] > arr[(index - 1) / 2]) {
			swap(arr, index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

    /**
     * 大根堆中某个元素的值进行修改,通过heapify进行该值在大根堆中的位置，保证大根堆的正确
     * @param arr       存放元素的数组,包含了堆中的元素
     * @param index     当前节点的索引位置
     * @param heapSize  数组抽象成堆时,对的范围限制对应的索引
     */
	public static void heapify(int[] arr, int index, int heapSize) {
		// index位置的数值的左子节点对应数组中的位置
	    int leftNode = index * 2 + 1;
	    // 如果左子节点对应索引超出了堆的最大索引,就越界了
		while (leftNode < heapSize) {
		    // 如果右子节点也不越界,并且右子节点对应的数值大于左子节点对应的数值，就返回右子节点对应的索引,否则返回左子节点对应索引
			int largest = leftNode + 1 < heapSize && arr[leftNode + 1] > arr[leftNode] ? leftNode + 1 : leftNode;
			// 如果自节点中较大的那个大于此时节点的值,返回较大节点对应的索引,否则就直接返回自己的索引
			largest = arr[largest] > arr[index] ? largest : index;
			if (largest == index) {
				break;
			}
			// 交换子节点中较大位置的值与当前节点位置的值
			swap(arr, largest, index);
			// 当前节点的位置变为交换的子节点位置(往下沉)
			index = largest;
			// 获取此时的左子节点对应的索引位置
			leftNode = index * 2 + 1;
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// for test
	public static void comparator(int[] arr) {
		Arrays.sort(arr);
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			heapSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		heapSort(arr);
		printArray(arr);
	}

}
