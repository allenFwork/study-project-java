package com.study.fork_join.arraysum;

import com.study.fork_join.util.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 通过线程池的方式 实现并发处理集合元素数据相加 功能
 */
public class SumMultiThreads {
    // 每个线程处理的元素个数
    public final static int NUM = 1000;

    public static long sum(int[] arr, ExecutorService executor) throws Exception {
        long result = 0;
        // 所需要的线程数量
        int numThreads = arr.length / NUM > 0 ? arr.length / NUM : 1;

        // 每个线程平均处理的元素数量
        int num = arr.length / numThreads;
        // 通过线程数量来决定任务数量
        SumTask[] tasks = new SumTask[numThreads];
        Future<Long>[] sums = new Future[numThreads];
        for (int i = 0; i < numThreads; i++) {
            tasks[i] = new SumTask(arr, (i * NUM), ((i + 1) * NUM));
            sums[i] = executor.submit(tasks[i]);
        }

        for (int i = 0; i < numThreads; i++) {
            result += sums[i].get();
        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        int[] arr = Utils.buildRandomIntArray(200000);
        int numThreads = arr.length / NUM > 0 ? arr.length / NUM : 1;

        System.out.printf("The array length is: %d\n", arr.length);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        long result = sum(arr, executor);

        System.out.printf("The result is: %d\n", result);
    }
}
