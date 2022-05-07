package com.study.class_basic_01;

/**
 * 小和问题:
 * 描述：在一个数组中，每一个数左边比当前数小的数累加起来，叫做这个数组的小和。求一个数组的小和。
 * 例子：
 * -- [1,3,4,2,5]
 * -- 1左边比1小的数，没有；
 * -- 3左边比3小的数，1；
 * -- 4左边比4小的数，1、3；
 * -- 2左边比2小的数，1；
 * -- 5左边比5小的数，1、3、4、2；所以小和为1+1+3+1+1+3+4+2=16
 */
public class Code_12_SmallSum {

	// 计算数组的小和
    public static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        // 通过归并排序进行计算小和的值
        return mergeSort(arr, 0, arr.length - 1);
    }

    public static int mergeSort(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }
		/**
		 * mid = (left + right) / 2
		 * 可能存在 (left + right)的下标越界，
		 * 所以改为 mid = left + (right - left) / 2 , 能够防溢出
		 * 除以2是算术运算, 可以改为位运算，位运算更快，即 (right - left) >> 1 , 向右移动了1位，等价于除以2
		 */
		int mid = left + ((right - left) >> 1);
        return mergeSort(arr, left, mid) + mergeSort(arr, mid + 1, right) + merge(arr, left, mid, right);
    }

    public static int merge(int[] arr, int leftIndex, int middleIndex, int rightIndex) {
        int[] helpArray = new int[rightIndex - leftIndex + 1];
        int i = 0;
        int p1 = leftIndex;
        int p2 = middleIndex + 1;
        int result = 0;
        while (p1 <= middleIndex && p2 <= rightIndex) {
            result += arr[p1] < arr[p2] ? (rightIndex - p2 + 1) * arr[p1] : 0;
            helpArray[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= middleIndex) {
            helpArray[i++] = arr[p1++];
        }
        while (p2 <= rightIndex) {
            helpArray[i++] = arr[p2++];
        }
        for (i = 0; i < helpArray.length; i++) {
            arr[leftIndex + i] = helpArray[i];
        }
        return result;
    }

    // 计算小和值绝对正确的方法
    public static int comparator(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int result = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                result += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return result;
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
            if (smallSum(arr1) != comparator(arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

}
