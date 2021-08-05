package com.study.thread.use;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试固定线程大小的线程池使用
 */
public class WorkStart1 {

    public static void main(String[] args) {
        // 此处是Executors类，不是Executor接口
        // newFixedThreadPool方法：创建一个固定大小的线程池,此处是5个线程
        ExecutorService executorService = Executors.newFixedThreadPool(5);
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
