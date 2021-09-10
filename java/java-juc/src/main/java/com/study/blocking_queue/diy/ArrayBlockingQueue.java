package com.study.blocking_queue.diy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于数组实现阻塞队列
 * 使用 显示Lock 实现阻塞队列
 * 使用 Condition 可以有多个Condition类型的对象，从而有多个 waiting room
 *
 * @param <E>
 */
public class ArrayBlockingQueue<E> {

    // 放具体的元素
    final Object[] items;
    // 取指针
    int takeIndex;
    // 放指针
    int putIndex;
    // 计数
    int count;
    // 显示锁，放元素 和 拿元素时，同时使用这把锁
    final ReentrantLock lock;
    // 条件变量，表明非空
    private final Condition notEmpty;
    // 条件变量，表明非满
    private final Condition notFull;

    /**
     * Returns item at index i.
     */
    @SuppressWarnings("unchecked")
    final E itemAt(int i) {
        return (E) items[i];
    }

    public ArrayBlockingQueue(int capacity, boolean fair) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.items = new Object[capacity];
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    public ArrayBlockingQueue(int capacity) {
        this(capacity, false);
    }

    /**
     * 难点：
     * 1.为什么要加锁？
     * 因为当该线程进入到waiting room后，被唤醒后重新竞争锁，竞争成功后可能条件又不满足了
     * <p>
     * 2.为什么使用 while (count == items.length), 而不是用 if (count == items.length)？
     */
    public boolean put(E e) throws InterruptedException {
        checkNotNull(e);
        // 加锁，只有一个线程能够执行到下面的代码
        lock.lock();
        try {
            // 判断当前元素总个数count是否等于 数组容器的容量
            // 如果数组已经满了,就一直在这里等待下去，循环调用其方法
            while (count == items.length)
            /**
             * jdk 1.5 以后的 java.util.concurrent.locks.Condition 接口中 await()方法
             * 线程获取了锁，然后执行到这里，调用了Condition类型对象的await()方法，那么该线程就会进入等待区间（waiting room），然后释放获取的锁
             * 直到该线程在等待区间中接收到了之前Condition类型对象调用signalAll()方法的信息，才会重新开始竞争锁
             */
                notFull.await();
            items[putIndex] = e;
            if (++putIndex == items.length)
                putIndex = 0;
            count++;
            // 通知其他线程，该队列已经不是空的了，消费者可以消费了
            notEmpty.signalAll();

            return true;
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            // 如果队列是空的，一直循环执行等待
            while (count == 0)
                notEmpty.await();
            E x = (E) items[takeIndex];
            items[takeIndex] = null;
            if (++takeIndex == items.length)
                takeIndex = 0;
            count--;
            // 通知其他线程已经取了元素了，可以放元素了
            notFull.signalAll();
            return x;

        } finally {
            lock.unlock();
        }
    }

    private static void checkNotNull(Object v) {
        if (v == null)
            throw new NullPointerException();
    }
}
