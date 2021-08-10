package com.study.threadpool;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 自定义模拟一个任务
 */
public class Task implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running");
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
