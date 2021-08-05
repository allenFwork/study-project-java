package com.study.thread;

/**
 * 线程的实现方法一
 */
public class ThreadDemo extends Thread {

    @Override
    public void run() {
        System.out.println("ThreadDemo ... ");
    }
}
