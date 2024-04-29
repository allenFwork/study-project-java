package com.study.java8.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 查看线程：线程切换
 */
@Slf4j(topic = "c.Test1")
public class Test1 {
    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                log.debug("running...");
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                log.debug("running...");
            }
        }, "t2").start();
    }
}
