package com.study.synchronize;

import java.util.concurrent.TimeUnit;

public class SynchronizeDemo {

    // 1、同步方法
    // 1.1 修饰静态方法
    public synchronized static void accessResources0() {
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println(Thread.currentThread().getName() + " is running");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 1.2 非静态方法
    public synchronized void accessResources1() {
        try {
            TimeUnit.MINUTES.sleep(2);
            System.out.println(Thread.currentThread().getName() + " is running");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 2、同步代码块
    // 2.1 代码块 (对象)
    public void accessResources3() {
        synchronized (this) { // this指的是当前对象
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + " is running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 2.2 代码块 (CLASS类)
    public void accessResources4() {
        /**
         * 使用ClassLoader(类加载器)加载类(SynchrenizeDemo)到方法区时，会生成了一个Class对象到堆中
         * Class对象的所有对象都公用一个锁
         */
        synchronized (SynchronizeDemo.class) {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + " is running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {

//        for(int i=0; i<5; i++) {
//            new Thread(SynchronizeDemo::accessResources0).start();
//        }

        final SynchronizeDemo demo1 = new SynchronizeDemo();
        for (int i = 0; i < 5; i++) {
            new Thread(demo1::accessResources1).start();
        }

    }
}