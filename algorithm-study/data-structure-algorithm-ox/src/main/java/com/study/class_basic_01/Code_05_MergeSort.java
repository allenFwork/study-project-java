package com.study.class_basic_01;

import java.util.Arrays;

/**
 * 归并排序：
 * 时间复杂度 = O(N * logN)
 */
public class Code_05_MergeSort {

    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    /**
     * 归并排序的实现：
     * 1.对数组arr从leftIndex到rightIndex位置的数值进行排序
     * 2.通过了递归的方法实现
     *
     * @param arr        要排序的数组
     * @param leftIndex  要排序的起始位置
     * @param rightIndex 要排序的终止位置
     */
    public static void mergeSort(int[] arr, int leftIndex, int rightIndex) {
        // 如果要排序的数组的元素只有一个，直接返回
        if (leftIndex == rightIndex) {
            return;
        }
        // 获取leftIndex与rightIndex的中点的位置，等同于 (leftIndex + rightIndex) / 2
        int mid = leftIndex + ((rightIndex - leftIndex) >> 1);
        // 第一步：对分成两分的数组进行排序,对样本量进行取一半
        mergeSort(arr, leftIndex, mid);                       // T(N/2)
        mergeSort(arr, mid + 1, rightIndex);        // T(N/2)
        // 第二步：对已将排好序左半部分与右半部分进行外排(真正的排序)
        merge(arr, leftIndex, mid, rightIndex);              // O(N)
    }

    public static void merge(int[] arr, int leftIndex, int midIndex, int rightIndex) {
        // 创建了一个帮助数组,用来存储排序后的结果
        int[] helpArray = new int[rightIndex - leftIndex + 1];
        int i = 0;
        // 类似于指针的作用,指向当前正在进行比较的两个元素
        int p1 = leftIndex;
        int p2 = midIndex + 1;
        while (p1 <= midIndex && p2 <= rightIndex) {
            // 如果数组p1位置的数值小于数组p2位置的数值,将p1位置的数值添加到helpArray数组中,并将数组索引p1进行加一处理
            // 如果数组p1位置的数值大于数组p2位置的数值,将p2位置的数值添加到helpArray数组中,并将数组索引p2进行加一处理
            // 无论进行了那个判断,i都会进行加一
            helpArray[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 两个数组对应的索引必有一个越界,且只有一个越界
        while (p1 <= midIndex) {
            helpArray[i++] = arr[p1++];
        }
        while (p2 <= rightIndex) {
            helpArray[i++] = arr[p2++];
        }
        // 将helpArray数组的数据填会原来arr数组中,就是排好序的数组
        for (i = 0; i < helpArray.length; i++) {
            arr[leftIndex + i] = helpArray[i];
        }
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
            mergeSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        mergeSort(arr);
        printArray(arr);

    }

}
