package com.study.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorTest {

    public static void main(String[] args) {
        /*
         *
         */
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        // 开线程执行
        scheduledExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is ScheduledExecutorService ... ");
            }
        });

        // schedule方法：多长时间后执行，只执行一次
        scheduledExecutorService.schedule(() -> {
            System.out.println("this is scheduled() .... ");
        }, 5, TimeUnit.SECONDS);

        // scheduleAtFixedRate方法：多长时间后执行，执行后每个多长时间接着执行
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is scheduleAtFixedRate() ... ");
            }
        }, 10, 2, TimeUnit.SECONDS);

//        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 15, 1, TimeUnit.SECONDS);
    }

}
