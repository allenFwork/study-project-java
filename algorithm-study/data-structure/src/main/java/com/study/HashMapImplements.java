package com.study;

/**
 * HashMap的是实现
 *
 * @param <K> 泛型
 * @param <V> 泛型
 */
public class HashMapImplements<K, V> {

    // HashMap默认 底层数据结构 大小(数量必须是2的整数次幂,能够尽可能地解决hash冲突)
    private static final int DEFAULT_SIZE = 1 << 4;

    // 底层存储的位置：数组中
    private Entry<K, V> data[];

    // HashMap底层数组的实际最大容量
    private int capacity;
    // HashMap底层数组中包含实际的键值对 对象数量大小
    private int size;

    /**
     * HashMap底层的数组结构
     *
     * @param <K>
     * @param <V>
     */
    private class Entry<K, V> {
        K key;
        V value;

        Entry<K, V> next;

        public Entry() {

        }

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    // 默认的构造方法
    public HashMapImplements() {
        this(DEFAULT_SIZE);
    }

    // 设置容量的构造方法
    public HashMapImplements(int capacity) {
        if (capacity > 0) {
            data = new Entry[capacity];
            this.capacity = capacity;
            size = 0;
        } else {

        }
    }

    /**
     * 哈希算法：用来计算发出哈希值
     *
     * @param key
     * @return
     */
    public int hash(K key) {
        int hashValue = 0;
        if (key == null)
            hashValue = 0;
        else {
            hashValue = key.hashCode() ^ (hashValue >>> 16);
        }
        return hashValue % capacity;
    }

    private void put(K key, V value) {
        if (key == null) {
            // 抛出异常
        }
        int hashValue = hash(key);
        Entry<K,V> nextEntry = new Entry<>(key, value, null);
        Entry<K,V> hashMemory = data[hashValue];
    }

}
