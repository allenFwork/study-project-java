package com.study.class_basic_01;

import java.util.Arrays;

/**
 * 快速排序: 经典快排、随机快排（非常常用）
 * 此处使用的递归的方式实现的，但是在项目工程中都不使用递归的方式进行实现快速排序
 */
public class Code_04_QuickSort {

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            // 经典快排总是拿最后一个数值作为划分左右的标准，但是如果数组已经按照由小到大排好序了，那么以最右边值作为标准时，此时的时间复杂度为 O(N^2)
			// 随即交换数组中任意位置的一个数值与数组最右侧的数值，演变为了随机快排 期望是 O(N*logN)
            swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
            int[] p = partition(arr, left, right);
            quickSort(arr, left, p[0] - 1);
            quickSort(arr, p[1] + 1, right);
        }
    }

    /**
     * 思路：
     * * 以数组所给范围，下标为 left 到 right 的元素为范围进行操作
     * * 取最右边的数值作为比较对象，即数组中下标为right的数为比较对象number
     * * 将此时right设置为大于number数值的起始区域，left-1设置小于number数值的终止区域（假设上）
     * * 比较此时第一个元素的值(arr[left])是否大于number：
     * * 如果小于number，就将此时的这个值划分到小于区域，less进行加一（小于区域扩大），left也要加一（当前比较的位置向后一位）
     * * 如果等于number，直接将 left加一（当前比较的位置向后一位）
     * * 如果大于number，就将此时的值与最后倒数第二位(arr[right-1])的数值进行交换，more进行减一（大于区域扩大，变成了有一个元素），接着比较当前位置与最后一个位置的值大小，即与number比较
     * * ... 进行以上操作，直到最后
     * * 将最后一位与此时的大于区域的左边界位置上数值交换数值，结果就是左边全是小于等于number，右边全是大于number的数
     *
     * @param arr
     * @param left
     * @param right
     * @return
     */
    public static int[] partition(int[] arr, int left, int right) {
        int less = left - 1;
        int more = right;
        while (left < more) {
            if (arr[left] < arr[right]) {
                swap(arr, ++less, left++);
            } else if (arr[left] > arr[right]) {
                swap(arr, --more, left);
            } else {
                left++;
            }
        }
        swap(arr, more, right);
        return new int[]{less + 1, more};
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
            quickSort(arr1);
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
        quickSort(arr);
        printArray(arr);

    }

}
