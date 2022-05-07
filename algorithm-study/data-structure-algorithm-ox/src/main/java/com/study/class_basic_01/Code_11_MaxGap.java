package com.study.class_basic_01;

import java.util.Arrays;

/**
 * 问题：最大差值问题
 * - 给定一个数组，求如果排序之后，相邻两数的最大差值，要求时间复杂度O(N)，且要求不能用非基于比较的排序。
 * 思路：桶排序的思想
 */
public class Code_11_MaxGap {

    public static int maxGap(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        // 记录数组的长度
        int size = arr.length;
        // 设置最小值（假设的）
        int min = Integer.MAX_VALUE;
        // 设置最大值（假设的）
        int max = Integer.MIN_VALUE;
        // 遍历数组找出最大值和最小值
        for (int i = 0; i < size; i++) {
            min = Math.min(min, arr[i]);
            max = Math.max(max, arr[i]);
        }
        if (min == max) {
            return 0;
        }
        // 桶中是否存放了数据
        boolean[] hasNum = new boolean[size + 1];
        // 桶中存放的最大值
        int[] bucketMax = new int[size + 1];
        // 桶中存放的最小值
        int[] bucketMin = new int[size + 1];
        int bid = 0;
        for (int i = 0; i < size; i++) {
            // 确定数组中当前位置的数应该放到哪个桶里，即桶数组对应的下标
            bid = bucket(arr[i], size, min, max);
            bucketMin[bid] = hasNum[bid] ? Math.min(bucketMin[bid], arr[i]) : arr[i];
            bucketMax[bid] = hasNum[bid] ? Math.max(bucketMax[bid], arr[i]) : arr[i];
            hasNum[bid] = true;
        }
        // 源数组中相邻两个数之间差值的最大值
        int result = 0;
        // 每个桶中的最大值,初始化设为第一个桶中最大值
        int lastMax = bucketMax[0];
        // 从第二个桶开始,一个桶里的内部的差值不可能是最大的
        int i = 1;
        for (; i <= size; i++) {
            if (hasNum[i]) {
            	// 后一个有值的桶的最小值减去前一个有值桶的最大值,与目前的最大差值作比较,大的存到result中
                result = Math.max(result, bucketMin[i] - lastMax);
                // 更新此时的同种最大值
                lastMax = bucketMax[i];
            }
        }
        return result;
    }

	/**
	 * 返回桶对应数组的下标索引
	 * @param num 具体的数值,从源数组中取出的
	 * @param len
	 * @param min 源数组中的最小值,确定桶的下边界
	 * @param max 源数组中的最大值,确定桶的上边界
	 * @return
	 */
    public static int bucket(long num, long len, long min, long max) {
        return (int) ((num - min) * len / (max - min));
    }

    // for test
    public static int comparator(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        Arrays.sort(nums);
        int gap = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            gap = Math.max(nums[i] - nums[i - 1], gap);
        }
        return gap;
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
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (maxGap(arr1) != comparator(arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

}
