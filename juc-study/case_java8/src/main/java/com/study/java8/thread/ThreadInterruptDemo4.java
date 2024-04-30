package com.study.java8.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TwoPhaseTermination")
public class ThreadInterruptDemo4 {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        /*tpt.start();
        tpt.start();
        tpt.start();*/

        tpt.start();
        Thread.sleep(3500);
        log.debug("停止监控");
        tpt.stop();
    }
}

/**
 * 设计模式：两阶段终止模式
 */
@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {
    // 监控线程
    private Thread monitorThread;
    // 停止标记
    private volatile boolean stop = false;
    // 判断是否执行过 start 方法
    private boolean starting = false;

    // 启动监控线程
    public void start() {
        synchronized (this) {
            if (starting) { // false
                return;
            }
            starting = true;
        }
        monitorThread = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                // 是否被打断
                if (stop) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    // 监控程序不需要一直执行，只需要每隔1秒监控一次
                    Thread.sleep(1000); // 情况1：此时线程的状态是阻塞的
                    log.debug("执行监控记录");  // 情况2：此时线程的状态是RUNNING，如果此时被打断，那么会等这里的代码执行完，才会重置打断标记为真
                } catch (InterruptedException e) {
                    /*
                     * 当前线程执行了sleep方法，正处于阻塞状态(TIME WAITING)，此时如果被打断，会报 InterruptedException 异常，从而被捕获
                     */
                    // 1.处理异常
                    e.printStackTrace();
                    // 2.此时该监控的打断标记还是false，需要修改线程的打断标记，从而在下次循环中结束监控线程
                    currentThread.interrupt();
                }
            }
        }, "monitor");
        monitorThread.start();
    }

    // 停止监控线程
    public void stop() {
        stop = true;
        monitorThread.interrupt();
    }
}
