package com.study.jvm.actual_combat.memory_leak.demo5;

import java.util.concurrent.*;

/**
 * 内存泄露问题：ThreadLocal的使用（错误情况）
 * 如果仅仅使用手动创建的线程，就算没有调用ThreadLocal的remove方法清理数据，也不会产生内存泄漏。
 * 因为当线程被回收时，ThreadLocal也同样被回收。但是如果使用线程池就不一定了。
 */
public class Demo5 {
    public static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Integer.MAX_VALUE, Integer.MAX_VALUE,
                0, TimeUnit.DAYS, new SynchronousQueue<>());
        int count = 0;
        while (true) {
            System.out.println(++count);
            threadPoolExecutor.execute(() -> {
                threadLocal.set(new byte[1024 * 1024]); // 向ThreadLocal中存放1M数据，发生内存泄漏，解决办法通过 threadLocal.remove() 处理
                // threadLocal.remove();
            });
            Thread.sleep(10);
        }
    }
}
