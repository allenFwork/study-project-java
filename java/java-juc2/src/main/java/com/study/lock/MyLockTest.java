package com.study.lock;

/**
 * 测试使用用自定义的锁：
 *   是否出现重复的值
 */
public class MyLockTest {

    private MyLock myLock = new MyLock();
    private int m = 0;

    public int next() {
        try {
//            TimeUnit.SECONDS.sleep(2);

            // 使用自定义的锁
            myLock.lock();
            try {
                return m++;
            } finally {
                myLock.unlock();
            }

//            // 不使用锁
//            return m++;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("出现了异常");
        }
    }

    public static void main(String[] args) {
        MyLockTest myLockTest = new MyLockTest();
        Thread[] threads = new Thread[20];
        for (int i = 0; i < 20; i++) {
            threads[i] = new Thread(() -> {
                System.out.println(myLockTest.next());
            });
            threads[i].start();
        }
    }
}
