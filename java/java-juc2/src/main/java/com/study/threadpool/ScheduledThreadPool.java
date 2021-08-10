package com.study.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 可调度线程池：newScheduledThreadPool
 */
public class ScheduledThreadPool {

    public static void main(String[] args) {
        // 创建可调度的线程池
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        // 处理十个任务
        for (int i = 0; i < 10; i++) {
            Runnable task = new Task();
            // 把任务交给pool去执行
            pool.execute(task);
        }
        // 线程池有生命周期：关闭线程池
        pool.shutdown(); // shutdown
    }
}
