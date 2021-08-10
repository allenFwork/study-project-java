package com.study.threadpool;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolState {

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    public static void main(String[] args) {

        // 打印线程数的位数 和 位数对应的二进制位数
        System.out.println(COUNT_BITS + ":" + Integer.toBinaryString(COUNT_BITS));
        System.out.println(CAPACITY + ":" + Integer.toBinaryString(CAPACITY));
        // 线程池的 RUNNING 状态
        System.out.println(RUNNING + ":" + Integer.toBinaryString(RUNNING));
        // 线程池的 SHUTDOWN 状态
        System.out.println(SHUTDOWN + ":" + Integer.toBinaryString(SHUTDOWN));
        // 线程池的 STOP 状态
        System.out.println(STOP + ":" + Integer.toBinaryString(STOP));
        // 线程池的 TIDYING 状态
        System.out.println(TIDYING + ":" + Integer.toBinaryString(TIDYING));
        // 线程池的 TERMINATED 状态
        System.out.println(TERMINATED + ":" + Integer.toBinaryString(TERMINATED));

    }
}
