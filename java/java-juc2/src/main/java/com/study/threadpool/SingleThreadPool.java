package com.study.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建只有一个线程的线程池 newSingleThreadExecutor()
 *
 * 作用：减少了线程的创建和销毁的开销，能够一直用来处理不同任务
 */
public class SingleThreadPool {

    public static void main(String[] args) {
        // 创建单线程的线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();
        // 处理十个任务
        for (int i = 0; i < 10; i++) {
            // 创建任务
            Runnable task = new Task();
            // 把任务交给pool去执行
            pool.execute(task);
        }
    }

}
