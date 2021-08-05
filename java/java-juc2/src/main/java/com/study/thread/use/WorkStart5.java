package com.study.thread.use;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WorkStart5 {

    public static void main(String[] args) {

        // 不知道有多少个任务，所用将其放到队列中
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(10);

        //  创建线程池 ThreadPoolExecutor
        // 核心线程数量（最小数量）、最大数量、
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 1000, TimeUnit.MILLISECONDS, blockingQueue);

        // 创建30个任务去让线程池处理
        for (int i = 0; i < 30; i++) {
            WorkerThread workerThread = new WorkerThread("Thread" + i);
            threadPoolExecutor.execute(workerThread);
        }
        // 顺序关闭任务
        threadPoolExecutor.shutdown();
        while (!threadPoolExecutor.isTerminated()) {

        }
        System.out.println("All Thread works is finished.");
    }

}
