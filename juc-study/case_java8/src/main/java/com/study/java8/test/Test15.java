package com.study.java8.test;

import com.study.java8.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * 主线程与守护线程
 */
@Slf4j(topic = "c.Test15")
public class Test15 {
    public static void main(String[] args) throws InterruptedException {
        log.debug("开始运行... ");
        Thread t1 = new Thread(() -> {
//            while (true) {
//                if (Thread.currentThread().isInterrupted()) {
//                    break;
//                }
//            }
            Sleeper.sleep(2);
            log.debug("结束");
        }, "t1");
        // 将t1线程设置为守护线程：当其他非守护线程全部执行完成后，那么该线程将被强制停止
        t1.setDaemon(true);
        t1.start();

        Sleeper.sleep(3);
        log.debug("运行结束... ");
    }
}
