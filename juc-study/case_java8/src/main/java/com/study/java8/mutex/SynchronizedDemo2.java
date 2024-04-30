package com.study.java8.mutex;

import lombok.extern.slf4j.Slf4j;

/**
 * 代码优化
 * 通过 synchronized(对象) 的方式获取对象锁解决共享资源问题
 */
@Slf4j(topic = "c.SynchronizedDemo2")
public class SynchronizedDemo2 {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decrement();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}", room.getCounter());
    }
}

// 将共享资源放到该类中
class Room {
    private int counter = 0;

    // synchronized加在方法上等价于 synchronized(this)，它也是锁在对象上
    public synchronized void increment() {
        counter++;
    }

    // increment2()方法 与 increment()方法 等价
    public void increment2() {
        synchronized (this) {
            counter++;
        }
    }

    public synchronized void decrement() {
        counter--;
    }

    // 获取该资源时，也必须加锁，避免获取的是中间数据，不是最终数据
    public synchronized int getCounter() {
        return counter;
    }
}
