package com.study.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo2 {

    private volatile static int m = 0;

    private static AtomicInteger atomicI = new AtomicInteger(100);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(atomicI.compareAndSet(100, 110));
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicI.compareAndSet(110, 100));
        });
        t2.start();

        Thread t3 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicI.compareAndSet(100, 120));
        });
        t3.start();
    }

}
