package com.study.java8.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 打断阻塞：打断由sleep方法导致的阻塞
 */
@Slf4j(topic = "c.ThreadInterruptDemo1")
public class ThreadInterruptDemo1 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                log.debug("Running...");
            }
        }, "t1");

        t1.start();
        // 让主线程睡眠1秒
        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
        log.debug("打断标记:{}", t1.isInterrupted());
        log.debug("t1现成的状态:{}", t1.getState()); // t1现成的状态:RUNNABLE
    }
}
