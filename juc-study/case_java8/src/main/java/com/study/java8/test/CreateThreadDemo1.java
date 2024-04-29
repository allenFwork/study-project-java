package com.study.java8.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建线程学习
 */
@Slf4j(topic = "c.CreateThreadDemo1")
public class CreateThreadDemo1 {

    public static void main(String[] args) {
        // 构造方法的参数是给线程指定名字，推荐
        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.debug("running");
            }
        };
        // 指定该线程名字
        t1.setName("t1");
        // 启动线程
        t1.start();

        // 使用Lambda表达式
        Thread t2 = new Thread(() -> {
            log.debug("running");
        }, "t2");
        t2.start();

        // 主线程打印日志
        log.debug("running");
    }
}
