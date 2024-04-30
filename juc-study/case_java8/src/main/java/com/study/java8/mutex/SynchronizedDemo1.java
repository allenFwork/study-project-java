package com.study.java8.mutex;

import lombok.extern.slf4j.Slf4j;

/**
 * 通过 synchronized(对象) 的方式获取对象锁解决共享资源问题
 */
@Slf4j(topic = "c.SynchronizedDemo1")
public class SynchronizedDemo1 {

    private static int counter = 0;
    // 专门用来获取对象锁的对象
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock) {
                    counter++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock) {
                    counter--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}", counter);
    }
}
