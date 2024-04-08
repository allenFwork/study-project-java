package com.study.jvm.actual_combat.memory_leak.demo5;

/**
 * 内存泄露问题：ThreadLocal的使用（正常情况）
 * 如果仅仅使用手动创建的线程，就算没有调用ThreadLocal的remove方法清理数据，也不会产生内存泄漏。
 * 因为当线程被回收时，ThreadLocal也同样被回收。但是如果使用线程池就不一定了。
 */
public class Demo5_1 {
    public static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            new Thread(() -> {
                threadLocal.set(new byte[1024 * 1024 * 10]); // 在ThreadLocal中存放了10M数据
            }).start();
            Thread.sleep(10);
        }
    }
}
