package com.study.fork_join.recursivetask;

import java.util.concurrent.RecursiveTask;

/**
 * The class first sums an array sequentially then sums the array using the F/J framework.
 * This proves that for < 100 computational steps, sequential is better.
 * <p>
 * To prove that for > 100 computational steps, F/J is better, change boolean: extraWork = true;
 */
class LongSum extends RecursiveTask<Long> {

    static final int SEQUENTIAL_THRESHOLD = 1000;
    static final long NPS = (1000L * 1000 * 1000);
    static final boolean extraWork = true; // change to add more than just a sum

    int low;
    int high;
    int[] array;

    LongSum(int[] arr, int lo, int hi) {
        array = arr;
        low = lo;
        high = hi;
    }

    protected Long compute() {

        if (high - low <= SEQUENTIAL_THRESHOLD) {

            long sum = 0;
            for (int i = low; i < high; ++i) {
                sum += array[i];
                // for non-trivial work
                 // if (extraWork)
                 //Utils.doCpuIntensiveCalculation();
            }

            return sum;

        } else {
            int mid = low + (high - low) / 2;
            LongSum left = new LongSum(array, low, mid);
            LongSum right = new LongSum(array, mid, high);
            // 拆出一个线程，去执行左边数组的任务
            left.fork();
            // 直接使用当前线程执行右边数组的任务
            long rightAns = right.compute();
            // 等待左边数组执行的结果，当前线程阻塞在这里
            long leftAns = left.join();
            return leftAns + rightAns;
        }
    }
}

       