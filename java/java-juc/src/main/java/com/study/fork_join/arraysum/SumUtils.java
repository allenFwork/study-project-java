package com.study.fork_join.arraysum;

public class SumUtils {

    public static long sumRange(int[] arr, int start, int end) {
        long result = 0;

        for (int j = start; j < end; j++)
            result += arr[j];

        return result;
    }

}
