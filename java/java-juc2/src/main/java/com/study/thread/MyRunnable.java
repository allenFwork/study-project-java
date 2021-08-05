package com.study.thread;

/**
 * 线程实现方法二：实现Runnable
 */
public class MyRunnable implements Runnable {

    private int tickets = 10;

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            if (tickets > 0) {
                System.out.println("卖票：" + tickets--);
            }
        }
    }

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        new Thread(myRunnable).start();
        new Thread(myRunnable).start();
        new Thread(myRunnable).start();
    }

}
