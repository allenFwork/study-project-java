package com.study.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    public static void main(String[] args) {
        // Executors.newCachedThreadPool()底层使用的就是 ThreadPoolExecutor
        ExecutorService executorService = Executors.newCachedThreadPool();

        // submit底层还是execute方法
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("this is ThreadPoolTest ... ");
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("this is ThreadPoolTest ... ");
            }
        });

    }

}
