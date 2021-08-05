package com.study.thread.use;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试可变线程大小的线程池使用
 *  Executors.newCachedThreadPool()
 *  有多少个任务，线程池终究创建多少个线程
 */
public class WorkStart2 {

    public static void main(String[] args) {
        // newCachedThreadPool方法：创建一个可变大小的线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
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
