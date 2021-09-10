package com.study.threadpool.customized;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义线程池
 */
public class ThreadPoolExecutor extends AbstractExecutorService {

    private volatile int corePoolSize;
    private volatile int maxPoolSize;
    private volatile long keepAliveTime;
    // 描述是否需要超时
    private volatile boolean allowCoreThreadTimeOut;
    private BlockingQueue<Runnable> workQueue;
    // 当前任务的数量
    private final AtomicInteger ctl = new AtomicInteger(0);

    /**
     * 暴露的接口，然后接受task任务的
     *
     * @param command
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        int count = ctl.get();
        if (count <= corePoolSize) {
            addWorker(command, true);
        } else {
            // 将任务放到等待队列中
            boolean flag = workQueue.add(command);
            // 成功放入到队列中
            if (flag) {
                addWorker(null, false);
            } else {
                // 拒绝该任务
                reject(command);
            }
        }
    }

    public ThreadPoolExecutor(int corePoolSize, int maxPoolSize, long keepAliveTime, BlockingQueue<Runnable> workQueue) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        if (keepAliveTime > 0)
            this.allowCoreThreadTimeOut = true;
        this.workQueue = workQueue;
    }

    public void addWorker(Runnable task, boolean coreFlag) {
        // 任务小于线程数,计数进行加一处理
        if (coreFlag)
            ctl.incrementAndGet();
        Worker worker = new Worker(task);
        worker.thread.start();
    }

    @Override
    public void shutdown() {

    }

    // 定义一个干活的人, 内部类
    class Worker extends ReentrantLock implements Runnable {

        private Runnable firstTask;
        // 线程对象，用来执行任务的线程
        private Thread thread;

        public Worker(Runnable firstTask) {
            this.firstTask = firstTask;
            // 将当前的Worker对象放入线程中，交由该线程来执行Worker任务,即Worker的run方法
            thread = new Thread(this);
        }

        @Override
        public void run() {
            runWorker(this);
        }

        private void runWorker(Worker worker) {
            // 使用锁模板
            try {
                // 加锁
                worker.lock();
                Runnable task = worker.firstTask;
                // 如果此次传入的任务为空,那么就尝试从等待队列中获取任务
                if (task != null || (task = getTask()) != null) {
                    task.run();
                }
            } finally {
                // 任务执行完成后，进行回收Worker对象
                processWorkerExit(worker);
                // 释放锁
                worker.unlock();
            }

        }
    }

    // 从队列中获取任务
    public Runnable getTask() {
        try {
            if (workQueue.isEmpty()) {
                return null;
            }
            Runnable r = allowCoreThreadTimeOut ? workQueue.poll(keepAliveTime, TimeUnit.SECONDS) : workQueue.take();
            if (r != null) {
                return r;
            }
//            return workQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     */
    public void processWorkerExit(Worker worker) {
        addWorker(null, false);
    }

    public void reject(Runnable command) {
        new RejectExecutionHandler().rejectHandler(command);
    }

    // 拒绝策略类, 内部类
    class RejectExecutionHandler {
        public void rejectHandler(Runnable command) {
            throw new RejectedExecutionException("这个任务：" + command + "，我拒绝了.");
        }
    }

}
