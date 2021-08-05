package com.study.cas;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {

    private volatile static int m = 0;

    // 申明原子类型的变量 atomicI
    private static AtomicInteger atomicI = new AtomicInteger(0);

    public static void increase() {
        m++;
    }

    public static void increase2() {
        // 实现自加 等于具有原子性的 ++i
        atomicI.incrementAndGet();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                CASDemo.increase();
            }).start();
        }
        System.out.println(m);

//        for(int i=0; i<20; i++){
//            new Thread(()->{
//                CASDemo.increase2();
//            }).start();
//        }
//        System.out.println("atomicI: " + atomicI.get());
        Thread[] tf = new Thread[20];
        for (int i = 0; i < 20; i++) {
            tf[i] = new Thread(() -> {
                CASDemo.increase2();
            });
            tf[i].start();
            /**
             *
             * 将使用join()方法加入到当前线程组中，使当前线程进入等待，
             * 直到使用join()方法的线程执行完毕，接着执行当前线程
             * 使用join()方法线程有了交互性
             */
            try {
                tf[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(atomicI.get());
    }
}
