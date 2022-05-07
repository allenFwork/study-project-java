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

    public static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
        // 上面代码实现的就是下面代码的逻辑
//        int temp = arr[i];
//        arr[i] = arr[j];
//        arr[j] = temp;
    }

    /*============================================ 对数器(开始) ============================================*/
    /**
     * 一个绝对正确的排序方法，用于验证结果是否正确的，
     * 该方法中使用的算法不要求时间复杂度的好坏，只要求结果一定正确
     *
     * @param arr 来排序的数组
     */
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    /**
     * 生成一个随机数组，用于测试的
     *
     * @param maxSize  生成出来数组的元素个数的最大值
     * @param maxValue 数组中数值的最大值
     * @return
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        /**
         * 生成长度随机的数组
         * Math.random()  表示生成一个 [0,1) 的随机数，该数是 double 类型的（等概率的）
         * (int) ((maxSize + 1) * Math.random()) 表示生成一个 [0,maxSize] 的随机一个整数（等概率的）
         */
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

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

    /**
     * 验证数组arr1和数组arr2对应位置上的数值是否相等
     * @param arr1
     * @param arr2
     * @return
     */
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

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 大样本测试
    public static void main(String[] args) {
        int testTime = 500000;
        int size = 10;
        int value = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(size, value);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            bubbleSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr3);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(size, value);
        printArray(arr);
        bubbleSort(arr);
        printArray(arr);
    }
    /*============================================ 对数器(结束) ============================================*/

}