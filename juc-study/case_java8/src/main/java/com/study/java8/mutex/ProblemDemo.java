package com.study.java8.mutex;

import lombok.extern.slf4j.Slf4j;

/**
 * 多线程同时处理共享资源出现问题演示
 * 多执行几次，每次的结果都可能不一样
 */
@Slf4j(topic = "c.ProblemDemo")
public class ProblemDemo {
    // 多个线程共享的资源
    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                counter++;
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                counter--;
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}", counter);
    }
}
