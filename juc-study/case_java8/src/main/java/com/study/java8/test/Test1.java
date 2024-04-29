package com.study.java8.test;

import lombok.extern.slf4j.Slf4j;

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
