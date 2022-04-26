package com.study.class_basic_01;

import java.util.Arrays;

/**
 * 冒泡排序：有N个数排序
 * 步骤一：
 * * 1. 将数组中第一个与与第二个进行比较，如果第一个大于第二个则交换位置，否则不变
 * * 2. 将数组中第二个与与第三个进行比较，如果第二个大于第三个则交换位置，否则不变
 * * 3. 。。。
 * * 4. 直到将第N-1个和第N个进行比较，前一个大于后一个，则交换位置
 * * 此时，就将数组中最大的数放到了最后一位
 * 步骤二：
 * * 1. 将数组中第一个与与第二个进行比较，如果第一个大于第二个则交换位置，否则不变
 * * 2. 将数组中第二个与与第三个进行比较，如果第二个大于第三个则交换位置，否则不变
 * * 3. 。。。
 * * 4. 直到将第N-2个和第N-1个进行比较，前一个大于后一个，则交换位置
 * * 此时，就将数组中最第二大的数放到了倒数第二位
 * ...
 */
public class Code_00_BubbleSort {

    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int end = arr.length - 1; end > 0; end--) {
            for (int i = 0; i < end; i++) {
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                }
            }
        }
    }

//    public static void swap(int[] arr, int i, int j) {
//        int temp = arr[i];
//        arr[i] = arr[j];
//        arr[j] = temp;
//    }

    public static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
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

}