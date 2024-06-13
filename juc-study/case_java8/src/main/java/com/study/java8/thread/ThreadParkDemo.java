package com.study.java8.thread;

import com.study.java8.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static com.study.java8.util.Sleeper.sleep;

@Slf4j(topic = "c.ThreadParkDemo")
public class ThreadParkDemo {

    private static void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();  // 执行这行语句，t1线程已经阻塞了(WAIT)，不会向下执行
            log.debug("unPark...");
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        Sleeper.sleep(1);
        log.error("执行 t1.interrupt() 。。。 ");
        t1.interrupt();         // 执行这行语句，t1会从park后的WAIT变为RUNNING状态，t1线程能接着向下执行
    }

    private static void test2() {
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park...");
                LockSupport.park();
                log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
                // 当线程的打断标记置为真后，再执行 LockSupport.park() 会失效，无法阻塞线程，通过 interrupted() 方法可以重置打断标记为假
//                log.debug("打断状态：{}", Thread.interrupted());
//                log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
            }
        }, "t2");
        t2.start();

        Sleeper.sleep(2);
        // 通过interrupt方法会将t1线程的打断标记置为真
        t2.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
//        test1();
        test2();
    }
}
