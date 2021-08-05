package com.study.thread.use;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试创建有多个线程的线程池，可调度执行
 *   Executors.newScheduledThreadPool()
 */
public class WorkStart4 {

    public static void main(String[] args) {

        // newScheduledThreadPool方法：创建有3个线程的线程池，可调度执行
        ExecutorService executorService = Executors.newScheduledThreadPool(3);

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
