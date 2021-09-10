package com.study.fork_join.arraysum;

import java.util.concurrent.Callable;

public class SumTask implements Callable<Long> {
    int start;
    int end;
    int[] arr;

    public SumTask(int[] a, int l, int h) {
        start = l;
        end = h;
        arr = a;
    }

    public Long call() { //override must have this type
        // System.out.printf("The range is [%d - %d]\n", lo, hi);
        long result = SumUtils.sumRange(arr, start, end);
        return result;
    }
}
