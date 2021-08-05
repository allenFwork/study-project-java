package com.study.concurrent;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 并发工具 CyclicBarrier
 * 需求：模拟赛跑，8名运动员都准好了，才开始起跑
 */
public class Race {

    public static void main(String[] args) {
        // step1: 给 CyclicBarrier 设置一个值,该值是需要等待完成线程任务的线程数量和
        CyclicBarrier cyclicBarrier = new CyclicBarrier(8);

        Thread[] players = new Thread[8];
        for (int i = 0; i < 8; i++) {
            players[i] = new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                    System.out.println(Thread.currentThread().getName() + "准备好了");
                    /*
                     * await()方法执行后，cyclicBarrier内部会进行加一处理:
                     * 直到数值等于一开始设置的8，线程才会向下执行;
                     * 否则线程进入阻塞状态，一直等待;
                     */
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("选手" + Thread.currentThread().getName() + "起跑");
            }, "players[" + i + "]");
            players[i].start();
        }
    }
}
