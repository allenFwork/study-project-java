package com.study.concurrent;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 并发工具 Semaphore（信号量）
 * 需求：停车厂进来了多辆车，进行停车行为
 */
public class Cars {

    public static void main(String[] args) {

        // step1: 创建Semaphore (信号量对象), 模拟停车场停车位只有5个
        Semaphore semaphore = new Semaphore(5);

        // 模拟10辆车要开进停车场
        Thread[] cars = new Thread[10];
        for (int i = 0; i < 10; i++) {
            cars[i] = new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    // step2: 获取请求许可
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "可以进入停车场.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // step3：使用资源 (车子在停车场中做自己的事)
                try {
                    int time = new Random().nextInt(10);
                    TimeUnit.SECONDS.sleep(time);
                    System.out.println(Thread.currentThread().getName() + "停留了" + time + "秒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // step4: 释放资源 （离开）
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + "离开停车场");
            }, "car[" + i + ']');

            cars[i].start();
        }
    }

}
