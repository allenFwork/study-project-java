package com.study.java8.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 打断阻塞：打断由sleep方法导致的阻塞
 */
@Slf4j(topic = "c.Test11")
public class ThreadInterruptDemo2 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("sleep...");
            try {
                Thread.sleep(5000); // wait, join
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        t1.start();
        // 让主线程睡眠1秒，保证此时t1线程此时执行了sleep方法，进入了阻塞状态
        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
        log.debug("打断标记:{}", t1.isInterrupted());
    }
}
