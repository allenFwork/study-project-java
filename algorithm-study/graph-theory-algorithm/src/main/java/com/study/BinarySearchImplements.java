package com.study;

/**
 * 二分查找算法的实现
 */
public class BinarySearchImplements {

    /**
     * 使用迭代实现二分查找算法
     *
     * @param data 查找的数组，已经排好序的数组
     * @param x    需要查找的数值
     * @param low
     * @param high
     * @return
     */
    public static int binarySearch(int[] data, int x, int low, int high) {
        // 使用的迭代的方法,一定要确认好退出条件,否则会出现栈溢出问题
        if (x < data[low] || x > data[high] || low > high)
            return -1;
        int mid = (low + high) / 2;
        if (x > data[mid])
            return binarySearch(data, x, mid - 1, high);
        else if (x < data[mid])
            return binarySearch(data, x, low, mid - 1);
        else
            return mid;
    }

    /**
     * 使用循环实现二分查找
     *
     * @param data 查找的数组，已经排好序的数组
     * @param x    需要查找的数值
     * @return
     */
    public static int binarySearch(int[] data, int x) {
        int low = 0;
        int high = data.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (x > data[mid])
                low = mid;
            else if (x < data[mid])
                high = mid;
            else
                return mid;
        }
        return -1;
    }

    public static void main(String[] args) {

    }

}
