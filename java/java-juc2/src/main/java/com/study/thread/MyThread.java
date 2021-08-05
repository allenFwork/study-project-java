package com.study.thread;

/**
 * 线程实现方法一：继承Thread
 */
public class MyThread extends Thread {

    private String info;

    public MyThread(String info) {
            this.info = info;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("name：" + i);
            try {
                // 休眠1秒钟
                sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyThread myThread1 = new MyThread("A");
        MyThread myThread2 = new MyThread("B");
        myThread1.start();
        myThread2.start();
    }
}
