package com.study.thread.use;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试可单个线程的线程池使用
 */
public class WorkStart3 {

    public static void main(String[] args) {

        // newSingleThreadExecutor方法：创建只有一个线程的线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // newSingleThreadScheduledExecutor方法：创建只有一个线程的线程池，但是任务可以调度的放入线程池中进行处理
        // ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        // 创建10个任务去让线程池处理
        for (int i = 0; i < 10; i++) {
            WorkerThread workerThread = new WorkerThread("Thread" + i);
            // ExecutorService接口是所有线程池的父接口,所以executorService可以看作是线程池
            executorService.execute(workerThread);
        }
        // 顺序关闭任务
        executorService.shutdown();
        while (!executorService.isTerminated()) {

        }
        System.out.println("All Thread works is finished.");
    }

}
