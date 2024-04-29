package com.study.java8.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * Thread的sleep方法，修改线程的状态为 TIMED_WAITING
 */
@Slf4j(topic = "c.Test6")
public class ThreadStatusDemo2 {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    // 休眠两秒
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        log.debug("t1 state: {}", t1.getState()); // t1 state: RUNNABLE

        try {
            // 当前线程休眠0.5秒
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("t1 state: {}", t1.getState()); // t1 state: TIMED_WAITING
    }
}
