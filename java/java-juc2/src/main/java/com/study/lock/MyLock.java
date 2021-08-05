package com.study.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * jdk提供了java.util.concurrent.locks.Lock接口，用于给用户实现锁
 * 利用 CAS 和 AQS 实现锁的功能
 */
public class MyLock implements Lock {

    private LockHelper lockHelper = new LockHelper();

    /**
     * 写一个内部帮助类，私有的内部类继承AQS：
     * 1. 私有的
     * 2. 必须继承 AbstractQueuedSynchronizer 抽象类
     *
     * 这个帮助器重写 AbstractQueuedSynchronizer 的两个方法，一个获取锁，一个释放锁
     */
    private class LockHelper extends AbstractQueuedSynchronizer {

        // 获取锁（独占锁的形式）
        @Override
        protected boolean tryAcquire(int arg) {
            // 获取state的值
            int state = getState();
            // 如果state等于0，表示能够获取到锁；state大于0，表示无法获取到锁
            if (state == 0) {
                // 利用CAS原理修改state,表示获取到锁了
                if (compareAndSetState(0, arg)) {
                    // 设置当前线程占有资源
                    setExclusiveOwnerThread(Thread.currentThread());// 即使不写，底层自动设置
                    return true;
                }
            // 表示该线程已经获取了该锁了，又一次尝试获取锁（重入锁实现）
            } else if (getExclusiveOwnerThread() == Thread.currentThread()) {
                // 修改状态值state，等于每次获取锁时，传入参数值相加的和
                setState(getState() + arg);
                return true;
            }
            // 获取锁失败,返回false
            return false;
        }

//        // 获取锁（共享锁）
//        @Override
//        protected int tryAcquireShared(int arg) {
//            return super.tryAcquireShared(arg);
//        }

        // 释放锁
        @Override
        protected boolean tryRelease(int arg) {
            // 获取state的值，并将其减去获取锁时设置的值（相加的值）
            int state = getState() - arg;
            boolean flag = false;
            // 判断释放后是否为0，如果为0，表示该锁已经没有被任何线程占有
            if (state == 0) {
                // 设置此时占有该锁的线程为null
                setExclusiveOwnerThread(null);
                setState(state);
                return true;
            }
            // 存在线程安全吗？ 重入性的问题，当前已经独占了资源，不存在线程安全问题
            setState(state);
            return false;
        }

        // 条件对象，在某些条件下进行加锁
        public Condition newConditionObject() {
            return new ConditionObject();
        }
    }

    // 自定义锁的加锁功能
    @Override
    public void lock() {
        lockHelper.acquire(1);
    }

    // 自定义锁的加锁功能
    @Override
    public void lockInterruptibly() throws InterruptedException {
        lockHelper.acquireInterruptibly(1);
    }

    // 自定义锁的加锁功能（尝试获取锁）
    @Override
    public boolean tryLock() {
        return lockHelper.tryAcquire(1);
    }

    // 自定义锁的加锁功能（有时间限制地获取锁）
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return lockHelper.tryAcquireNanos(1, unit.toNanos(time));
    }

    // 自定义锁的释放锁功能
    @Override
    public void unlock() {
        lockHelper.release(1);
    }

    // 条件对象，在某些条件下进行加锁
    @Override
    public Condition newCondition() {
        return lockHelper.newConditionObject();
    }
}
