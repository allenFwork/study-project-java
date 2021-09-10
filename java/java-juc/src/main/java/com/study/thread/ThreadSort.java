package com.study.thread;

/**
 * 确定线程的执行顺序
 * 使用 Thread 的 join 方法，实质就是调用 public final synchronized void join(long millis) 方法
 */
public class ThreadSort {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            System.out.println("thread1 ... ");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("thread2 ... ");
        });

        Thread thread3 = new Thread(() -> {
            System.out.println("thread3 ... ");
        });

        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        thread3.start();
        thread3.join();
    }

}
