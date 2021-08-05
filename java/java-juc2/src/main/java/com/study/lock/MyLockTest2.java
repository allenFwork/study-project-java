package com.study.lock;

/**
 * 测试锁的重复使用
 */
public class MyLockTest2 {

    private MyLock myLock = new MyLock();

    public void a() {
        myLock.lock();
        System.out.println("a() ... ");
        b();
        myLock.unlock();
    }

    public void b() {
        myLock.lock();
        System.out.println("b() ... ");
        myLock.unlock();
    }

    public static void main(String[] args) {
        MyLockTest2 myLockTest2 = new MyLockTest2();
        new Thread(() -> {
            myLockTest2.a();
        }).start();
    }
}
