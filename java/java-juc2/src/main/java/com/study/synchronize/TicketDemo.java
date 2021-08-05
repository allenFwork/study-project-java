package com.study.synchronize;

/**
 * 需求：编写一个类似银行、医院的叫号程序（要求：多个窗口叫号，不重号、不跳号）
 */
public class TicketDemo extends Thread {

    // 叫到的号码牌
    /*
     * private int index = 1; 私有的变量，每一个便变量都有自己的index
     * private static int index = 1; 共享变量
     */
    private static int index = 1;

    // 最多处理人数: 营业厅内最多只能处理50人
    private static final int MAX = 50;

    @Override
    public void run() {

        /**
         * 可能出现两种情况：跳号 和 重号
         * i = 50;  i+1; System.out.println(i);
         * 1.重号：
         *   线程1 从内存中获取i的到工作区间，执行加1操作，没来得及写入到内存中，
         *   线程2 就进行了同样的操作，并且写入到内存中，此时线程2输出结果是51，线程1输出结果也是51
         * 2.跳号：
         *   线程1 从内存中获取i的到工作区间，执行加以操作，写入到内存中，
         *   没有来得及执行写出操作到控制台，线程2就进行了同样的操作，此时线程2输出结果是52，线程1输出结果也是52
         */
//        while(index <= MAX){
//            System.out.println(Thread.currentThread().getName() + "叫到的号码是：" + (index++));
//        }

        /**
         * 使用sychronize封锁当前对象,解决上述出现的两种问题（重号和跳号）
         * 将 sychronize(this){ 代码块 } 中的代码块作为一个整体：
         * 1. 具体有原子性，不能只执行其中一部分代码，要么全执行
         * 2. 具有排他性，有一条线程在执行代码块时，即使其他的线程获取了CPU时间片，执行到该处就不能执行了，必须等上次的线程执行完成。
         */
        synchronized (this) {
            while(index <= MAX){
                System.out.println(Thread.currentThread().getName() + "叫到的号码是：" + (index++));
            }
        }

    }

    // main方法缩写方法： psvm
    public static void main(String[] args) {
        // 基于线程创建
        TicketDemo t1 = new TicketDemo();
        TicketDemo t2 = new TicketDemo();
        TicketDemo t3 = new TicketDemo();
        TicketDemo t4 = new TicketDemo();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
