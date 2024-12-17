package com.study.datastructure.linkedlist;

import java.util.Iterator;

/**
 * 循环双向链表(带哨兵)
 */
public class CircularDoublyLinkedListSentinel implements Iterable<Integer> {
    static class Node {
        Node prev; // 上一个节点指针
        int value; // 值
        Node next; // 下一个节点指针

        public Node(Node prev, int value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    // private Node sentinel = new Node(sentinel, -1, sentinel);
    // 上面这么写是错误的，因为此时 sentinel 还没有初始化，是 null

    // 创建空的循环双向链表，该链表只有一个哨兵，且指向自己
    private Node sentinel = new Node(null, -1, null); // 哨兵

    public CircularDoublyLinkedListSentinel() {
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    /**
     * 添加到第一个
     *
     * @param value 待添加的值
     */
    public void addFirst(int value) {
        Node a = sentinel;
        Node b = sentinel.next;
        Node addedNode = new Node(a, value, b);
        a.next = addedNode; // 哨兵指向新的第一个节点
        b.prev = addedNode; // 第二个节点指向第一个节点
    }

    /**
     * 删除第一个
     */
    public void removeFirst() {
        Node removedNode = sentinel.next;
        if (removedNode == sentinel) {
            throw new IllegalArgumentException("非法");
        }
        Node a = sentinel;
        Node b = removedNode.next;
        a.next = b;
        b.prev = a;
    }

    /**
     * 添加到最后一个
     *
     * @param value 待添加的值
     */
    public void addLast(int value) {
        Node a = sentinel.prev;
        Node b = sentinel;
        Node addedNode = new Node(a, value, b);
        a.next = addedNode;
        b.prev = addedNode;
    }

    public void removeLast() {
        Node removedNode = sentinel.prev;
        if (removedNode == sentinel) {
            throw new IllegalArgumentException("非法");
        }
        Node a = removedNode.prev;
        Node b = sentinel;
        a.next = b;
        b.prev = a;
    }

    /**
     * 根据值删除
     *
     * @param value - 目标值
     */
    public void removeByValue(int value) {
        for (Node p = sentinel.next; p != sentinel; p = p.next) {
            if (value == p.value) {
                Node removedNode = p;
                Node a = p.prev;
                Node b = p.next;
                a.next = b;
                b.prev = a;
            }
        }
    }

    private IllegalArgumentException illegalIndex(int index) {
        return new IllegalArgumentException(String.format("index [%d] 不合法%n", index));
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            // 从第一个节点开始遍历
            Node pointer = sentinel.next;

            @Override
            public boolean hasNext() {
                return pointer != sentinel;
            }

            @Override
            public Integer next() {
                int value = pointer.value;
                pointer = pointer.next;
                return value;
            }
        };
    }
}
