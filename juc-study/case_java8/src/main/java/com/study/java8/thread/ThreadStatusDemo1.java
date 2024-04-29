package com.study.java8.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 1.Thread启动后状态
 * 2.多次启动线程，出现的错误：
 *  Exception in thread "main" java.lang.IllegalThreadStateException
 * 	    at java.lang.Thread.start(Thread.java:708)
 * 	    at com.study.java8.test.Test5.main(Test5.java:22)
 */
@Slf4j(topic = "c.ThreadStatusDemo")
public class ThreadStatusDemo1 {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
            }
        };

        System.out.println(t1.getState()); // NEW，线程处于新建的状态
        t1.start();
        // 线程启动两次，查看错误
//        t1.start();
        System.out.println(t1.getState()); // RUNNABLE，线程处于可执行的状态
    }
}
