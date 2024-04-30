package com.study.java8.thread;

import com.study.java8.Constants;
import com.study.java8.util.FileReader;

/**
 * Java 的 Thread.State 枚举有六个值：NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED
 * Java线程的RUNNABLE状态包含了 操作系统层面的阻塞状态，如以下代码
 */
public class ThreadStatusDemo3 {
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            // 当Java线程读取文件时，会通过操作系统进行，操作系统在进行IO操作时，会阻塞，
            // 可是此时Java对应该线程的状态还是 RUNNABLE 状态
            FileReader.read(Constants.MP4_FULL_PATH);
            FileReader.read(Constants.MP4_FULL_PATH);
            FileReader.read(Constants.MP4_FULL_PATH);
        }, "t1").start();

        Thread.sleep(1000);
        System.out.println("ok");
    }
}
