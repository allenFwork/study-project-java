package com.study.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用jdk提供的锁
 */
public class LockTest {

    // 可重入锁
    private ReentrantLock reentrantLock = new ReentrantLock();

    public void a() {
        reentrantLock.lock();
        try {
            System.out.println("a() ... ");
            b();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void b() {
        reentrantLock.lock();
        try {
            System.out.println("b() ... ");
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) {
        LockTest lockTest = new LockTest();
        new Thread(() -> {
            lockTest.a();
        }).start();
    }
}
