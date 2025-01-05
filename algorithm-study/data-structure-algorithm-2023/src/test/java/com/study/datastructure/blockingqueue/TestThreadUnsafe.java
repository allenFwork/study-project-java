package com.study.datastructure.blockingqueue;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
    java中两种锁的选择：
    1. synchronized 关键字, 功能少
    2. ReentrantLock 可重入锁, 功能多
 */
public class TestThreadUnsafe {
    private final String[] array = new String[10];
    private int tail = 0;
    private int size = 0;
    ReentrantLock lock = new ReentrantLock();  // 锁对象
    Condition tailWaits = lock.newCondition(); // 条件变量对象，可以看作是一个集合

    public void offer(String e) throws InterruptedException {
//        lock.lock(); // 加锁,一个线程获取了锁，其余的线程都会阻塞，这些线程不会自动唤醒
        lock.lockInterruptibly(); // 加锁,为获取到锁的线程可以在阻塞状态随时打断，例如：阻塞了很长时间不想等待了自动唤醒报错
        try {
            // await()方法一般都会配合while使用，防止虚假唤醒，唤醒后重新检查条件，看是否得重新进入等待
            if (isFull()) {
                // 满了该做的事, offer 线程阻塞
                tailWaits.await(); // 当前线程加入 tailWaits, 并且让此线程阻塞。并且此时会释放掉lock锁
                // tailWaits.signal() 把集合中某个阻塞线程唤醒
            }
            array[tail] = e;
            if (++tail == array.length) {
                tail = 0;
            }
            size++;
        } finally {
            lock.unlock(); // 解锁
        }
    }

    private boolean isFull() {
        return size == array.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    public static void main(String[] args) throws InterruptedException {
        TestThreadUnsafe queue = new TestThreadUnsafe();
        for (int i = 0; i < 10; i++) {
            queue.offer("e" + i);
        }
        /*
         * 测试时注意点，Idea打断点设置为Thread，然后必须先让线程1执行完 queue.offer("e10"); 阻塞后，
         * 再执行 t2 线程中的唤醒操作，这样才能看到正常效果。
         *
         *  否则当线程2先执行完，那么已经尝试唤醒过了，此时再执行线程1的插入操作阻塞，程序会一直阻塞卡死。
         * 这是因为Idea打断点设置成了Thread模式，即使主线程结束了，因为有其他线程还在阻塞，程序就不会停止。
         */
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "添加元素之前");
                queue.offer("e10");
                System.out.println(Thread.currentThread().getName() + "添加元素成功");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t1").start();
//        new Thread(() -> {
//            try {
//                System.out.println(Thread.currentThread().getName() + "添加元素之前");
//                queue.offer("e11");
//                System.out.println(Thread.currentThread().getName() + "添加元素成功");
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }, "t1_1").start();

        new Thread(() -> {
            System.out.println("开始唤醒");
            try {
                queue.lock.lockInterruptibly();
                /*
                 * tailWaits 的 signal() 方法必须配合 lock使用，在使用前必须先获取锁，否则报错
                 * signal方法执行结束，不会立刻唤醒 tailWaits 中阻塞的一个线程，只有等 lock释放了后，阻塞线程才会被唤醒
                 */
                queue.tailWaits.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                queue.lock.unlock();
            }
        }, "t2").start();
    }

}
