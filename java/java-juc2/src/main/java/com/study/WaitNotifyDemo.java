package com.study;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.w3c.dom.ls.LSOutput;

/**
 * Java并发编程之Object.wait()/notify()详解
 * Java自带的Object对象中支持多线程的API
 */

public class WaitNotifyDemo {

    public static void main(String[] args) {

        Thread currentThread = Thread.currentThread();
        System.out.println("当前线程的名称：" + currentThread.getName());

        Object lock = new Object();
        System.out.println(currentThread.getName() + "线程创建第一个线程A，并执行线程A的任务");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread currentThread = Thread.currentThread();
                System.out.println("当前线程A的名称：" + currentThread.getName());
                System.out.println("线程A尝试获取lock锁");
                synchronized (lock) {
                    try {
                        System.out.println("线程A获取了lock锁");
                        Thread.sleep(1000);
                        System.out.println("线程A将要运行lock.wait()方法进行等待");
                        // 调用该方法的线程进入WAITING状态，只有等待另外线程的通知或被中断才会返回，
                        // 需要注意，调用wait()方法后，会释放对象的锁。
                        lock.wait();
                        System.out.println("线程A等待结束");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        System.out.println(currentThread.getName() + "线程创建第二个线程B，并执行线程B的任务");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread currentThread = Thread.currentThread();
                System.out.println("当前线程B的名称：" + currentThread.getName());
                System.out.println("线程B尝试获取lock锁");
                synchronized (lock) {
                    System.out.println("线程B获取了lock锁");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程B将要运行lock.notify()方法进行通知");
                    lock.notify();
                }
            }
        }).start();

        System.out.println(currentThread.getName() + "线程已经开始执行了线程A和B的任务了。。。");
    }
}