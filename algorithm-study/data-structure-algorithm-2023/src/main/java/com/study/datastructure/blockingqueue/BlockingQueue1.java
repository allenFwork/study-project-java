package com.study.datastructure.blockingqueue;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程安全的阻塞队列 - 单锁实现
 *
 * @param <E> 元素类型
 */
@SuppressWarnings("all")
public class BlockingQueue1<E> implements BlockingQueue<E> {

    private final E[] array; // 使用循环数组存放元素
    private int head;
    private int tail;
    private int size; // 元素个数

    public BlockingQueue1(int capacity) {
        array = (E[]) new Object[capacity];
    }

    private ReentrantLock lock = new ReentrantLock();
    private Condition headWaits = lock.newCondition(); // 配合获取使用
    private Condition tailWaits = lock.newCondition(); // 配合插入使用

    private boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return size == array.length;
    }

    @Override
    public void offer(E e) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            // await()方法一般都会配合while使用，防止虚假唤醒，唤醒后重新检查条件，看是否得重新进入等待
            while (isFull()) {
                tailWaits.await(); // 等待，等多久时间不确定
            }
            array[tail] = e;
            // 循环数组，当tail超出下标，重新定为0开始
            if (++tail == array.length) {
                tail = 0;
            }
            size++;
            headWaits.signal(); // 唤醒等待非空的线程（需要配合锁的获取与释放使用）
        } finally {
            lock.unlock();
        }
    }

    @Override // 传入参数timeout 单位：毫秒
    public boolean offer(E e, long timeout) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            // 将毫秒的时间转化为纳秒时间值
            long t = TimeUnit.MILLISECONDS.toNanos(timeout);
            while (isFull()) {
                if (t <= 0) {
                    // 不在尝试添加，返回false表示添加失败
                    return false;
                }
                /* 设置最多等待多少纳秒，返回值代表剩余时间
                 * 例如，需要等待5纳秒，已经等待过了1纳秒，然后被唤醒，结果又被别的线程使用了，
                 *      得重新等待，此时就是从4纳秒开始，不是原来的5纳秒计时。
                 */
                t = tailWaits.awaitNanos(t);
            }
            array[tail] = e;
            if (++tail == array.length) {
                tail = 0;
            }
            size++;
            headWaits.signal(); // 唤醒等待非空的线程（需要配合锁的获取与释放使用）
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E poll() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (isEmpty()) {
                headWaits.await();
            }
            E e = array[head];
            array[head] = null; // help GC
            if (++head == array.length) {
                head = 0;
            }
            size--;
            tailWaits.signal();// 唤醒等待非满的线程（需要配合锁的获取与释放使用）
            return e;
        } finally {
            lock.unlock();
        }
    }

    public E poll(long timeout) throws InterruptedException {
        // TODO 待完成
        return null;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue1<String> queue = new BlockingQueue1<>(3);
        queue.offer("任务1");

        new Thread(() -> {
            try {
                queue.offer("任务2");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "offer").start();

        new Thread(() -> {
            try {
                System.out.println(queue.poll());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "poll").start();
    }
}
