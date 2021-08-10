package com.study.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建线程方式二：创建可变大小的线程池 newCachedThreadPool
 */
public class CachedThreadPool {

    public static void main(String[] args) {
        // 创建可变大小的线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        // 处理十个任务
        for (int i = 0; i < 10; i++) {
            // 创建任务
            Runnable task = new Task();
            // 把任务交给pool去执行
            pool.execute(task);
        }

    }
}
