package com.study.fork_join.recursiveaction;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;

/**
 * 归并排序：两个排好序的数组进行归并，对应位置进行比较
 * Fork-join框架中：
 * 	 java.util.concurrent.RecursiveAction 无返回任务
 */
public class MergeSortAction extends RecursiveAction {

    private static final Logger LOGGER = Logger.getLogger(MergeSortAction.class.getName());
    // 阈值，数组元素个数比阈值小时，进行归并排序
    private final int threshold;
    private int[] arrayToSort;

    public MergeSortAction(final int[] arrayToSort, final int threshold) {
        this.arrayToSort = arrayToSort;
        this.threshold = threshold;
    }

    @Override
    protected void compute() {
        if (arrayToSort.length <= threshold) {
            // sequential sort
            Arrays.sort(arrayToSort);
            return;
        }

        // 将集合分为两个小的集合
        // Sort halves in parallel
        int midpoint = arrayToSort.length / 2;
        int[] leftArray = Arrays.copyOfRange(arrayToSort, 0, midpoint);
        int[] rightArray = Arrays.copyOfRange(arrayToSort, midpoint, arrayToSort.length);

        // 对左集合创建一个 fork-join框架 的无返回值任务
        MergeSortAction left = new MergeSortAction(leftArray, threshold);
        MergeSortAction right = new MergeSortAction(rightArray, threshold);

        // 将任务进行拆分执行
        // invokeAll(left, right);
        left.fork();
        right.fork();

        // 等待执行完成
        left.join();
        right.join();

        // sequential merge （执行归并排序）
        arrayToSort = MergeSort.merge(left.getSortedArray(), right.getSortedArray());
    }

    public int[] getSortedArray() {
        return arrayToSort;
    }

}