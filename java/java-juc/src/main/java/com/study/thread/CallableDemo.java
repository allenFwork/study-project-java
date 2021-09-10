package com.study.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 线程实现方法三
 * 使用 Callable 实现的线程执行任务过程后能够有返回值
 * Callable 添加了泛型机制（jdk1.5以后）
 */
public class CallableDemo implements Callable<String> {

    // 实现 Runnable 接口相比继承 Thread 类有如下优势
    // 1）可以避免由于 Java 的单继承特性而带来的局限
    // 2）增强程序的健壮性，代码能够被多个线程共享，代码与数据是独立的
    // 3）线程池只能放入实现 Runnable 或 Callable 类线程，不能直接放入继承 Thread 的类

    // 实现 Runnable 接口和实现 Callable 接口的区别
    // 1）Runnable 是自从 java1.1 就有了，而 Callable 是 1.5 之后才加上去的
    // 2）实现 Callable 接口的任务线程能返回执行结果，而实现 Runnable 接口的任务线程不能返回结果
    // 3）Callable 接口的 call()方法允许抛出异常，而 Runnable 接口的 run()方法的异常只能在内部消化，不能继续上抛
    // 4）加入线程池运行，Runnable 使用 ExecutorService 的 execute 方法，Callable 使用 submit 方法
    // 注：Callable 接口支持返回执行结果，此时需要调用 FutureTask.get()方法实现，
    //     此方法会阻塞主线程直到获取返回结果，当不调用此方法时，主线程不会阻塞
    @Override
    public String call() throws Exception {
        return "this is CallableDemo ... ";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * Callable 与 FutureTask 的一起使用
         * 异步获取线程执行返回的结果
         */
        FutureTask futureTask = new FutureTask(new CallableDemo());
        new Thread(futureTask).start();
        // FutureTask.get() 此方法会阻塞主线程直到获取返回结果，
        // 当不调用此方法时，主线程不会阻塞
        System.out.println(futureTask.get());
    }

}
