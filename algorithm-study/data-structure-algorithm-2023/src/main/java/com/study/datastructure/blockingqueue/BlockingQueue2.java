package com.study.datastructure.blockingqueue;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程安全的阻塞队列 - 双锁实现
 *
 * @param <E> 元素类型
 */
@SuppressWarnings("all")
public class BlockingQueue2<E> implements BlockingQueue<E> {

    private final E[] array;
    private int head;
    private int tail;
    // 用原子整数类保护数据在不同线程里的安全使用
    private AtomicInteger size = new AtomicInteger();

    // 堆poll操作的锁
    private ReentrantLock tailLock = new ReentrantLock();
    private Condition tailWaits = tailLock.newCondition();
    // 堆offer操作的锁
    private ReentrantLock headLock = new ReentrantLock();
    private Condition headWaits = headLock.newCondition();

    public BlockingQueue2(int capacity) {
        this.array = (E[]) new Object[capacity];
    }

    private boolean isEmpty() {
        return size.get() == 0;
    }

    private boolean isFull() {
        return size.get() == array.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    public void offer(E e) throws InterruptedException {
        int c; // 添加前元素个数
        tailLock.lockInterruptibly();
        try {
            // 1. 队列满则等待
            while (isFull()) {
                tailWaits.await(); //  offer2
            }

            // 2. 不满则入队
            array[tail] = e;
            if (++tail == array.length) {
                tail = 0;
            }

            // 3. 修改 size
            /*
                int size 的 size++ 不是原子操作，分为3步：
                1. 读取成员变量size的值  5
                2. 自增 6
                3. 结果写回成员变量size 6
             */
            c = size.getAndIncrement(); // c获取的size一开始的值，不是+1后的值
            if (c + 1 < array.length) {
                tailWaits.signal();
            }
        } finally {
            tailLock.unlock();
        }

        // 4. 如果从0变为非空，由offer这边唤醒等待非空的poll线程
        //                       0->1   1->2    2->3
        if (c == 0) {
            headLock.lock(); // offer_1 offer_2 offer_3
            try {
                headWaits.signal();
            } finally {
                headLock.unlock();
            }
        }
    }

    @Override
    public E poll() throws InterruptedException {
        E e;
        int c; // 取走前的元素个数
        headLock.lockInterruptibly();
        try {
            // 1. 队列空则等待
            while (isEmpty()) {
                headWaits.await(); // poll_4
            }

            // 2. 非空则出队
            e = array[head];
            array[head] = null; // help GC
            if (++head == array.length) {
                head = 0;
            }

            // 3. 修改 size
            c = size.getAndDecrement();
            // 3->2   2->1   1->0
            // poll_1 poll_2 poll_3
            if (c > 1) {
                headWaits.signal();
            }
            /*
                1. 读取成员变量size的值 5
                2. 自减 4
                3. 结果写回成员变量size 4
             */
        } finally {
            headLock.unlock();
        }

        // 4. 队列从满->不满时 由poll唤醒等待不满的 offer 线程
        if (c == array.length) {
            tailLock.lock();
            try {
                tailWaits.signal(); // 选中代码，ctrl+alt+t （Idea快捷键，出现try-catch选择）
            } finally {
                tailLock.unlock();
            }
        }

        return e;
    }

    @Override
    public boolean offer(E e, long timeout) throws InterruptedException {
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue2<String> queue = new BlockingQueue2<>(3);
        queue.offer("元素1");
        queue.offer("元素2");

        new Thread(() -> {
            try {
                queue.offer("元素3");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "offer").start();

        new Thread(() -> {
            try {
                queue.poll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "poll").start();
    }
}
