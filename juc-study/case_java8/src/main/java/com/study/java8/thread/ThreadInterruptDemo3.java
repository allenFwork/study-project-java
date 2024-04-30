package com.study.java8.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.ThreadInterruptDemo3")
public class ThreadInterruptDemo3 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                // 获取当前线程的打断标记，t1线程的打断标记
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted) {
                    log.debug("被打断了, 退出循环");
                    break;
                }
            }
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
    }
}
