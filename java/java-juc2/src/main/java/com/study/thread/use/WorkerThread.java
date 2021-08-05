package com.study.thread.use;

/**
 * 线程要执行的任务
 */
public class WorkerThread implements Runnable {

    private String info;

    public WorkerThread(String info) {
        this.info = info;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started. Information is " + this.info);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " is finished.");
    }

    @Override
    public String toString() {
        return this.info;
    }
}
