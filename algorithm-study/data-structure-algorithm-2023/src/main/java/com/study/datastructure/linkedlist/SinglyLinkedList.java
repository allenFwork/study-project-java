package com.study.datastructure.linkedlist;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * 单向链表
 */
public class SinglyLinkedList implements Iterable<Integer> { // 整体
    private Node head = null; // 头指针

    @Override
    public Iterator<Integer> iterator() {
        // 匿名内部类 -> 带名字内部类
        return new NodeIterator();
    }

    // 此处的内部类没有static修饰，因为该内部类引用了外部类的head变量，该变量与具体实例相关联，所以该内部类不能声明成static
    private class NodeIterator implements Iterator<Integer> {
        Node p = head;

        @Override
        public boolean hasNext() { // 是否有下一个元素
            return p != null;
        }

        @Override
        public Integer next() { // 返回当前值, 并指向下一个元素
            int v = p.value;
            p = p.next;
            return v;
        }
    }

    /**
     * 节点类
     * 该类声明时使用了 static 修饰，该内部类与外部类具体实例没有任何关系
     * （内部类能加static，最好加上）
     */
    private static class Node {
        int value; // 值
        Node next; // 下一个节点指针

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    /**
     * 向链表头部添加
     *
     * @param value 待添加值
     */
    public void addFirst(int value) {
        // 1. 链表为空
//        head = new Node(value, null);
        // 2. 链表非空
        head = new Node(value, head);
    }

    /**
     * 遍历链表1
     *
     * @param consumer 要执行的操作
     */
    public void loop1(Consumer<Integer> consumer) {
        Node currentNode = head;
        while (currentNode != null) {
            consumer.accept(currentNode.value);
            currentNode = currentNode.next;
        }
    }

    /**
     * 遍历链表2
     *
     * @param consumer 要执行的操作
     */
    public void loop2(Consumer<Integer> consumer) {
        for (Node currentNode = head; currentNode != null; currentNode = currentNode.next) {
            consumer.accept(currentNode.value);
        }
    }

    public void loop3(Consumer<Integer> before,
                      Consumer<Integer> after) {
        recursion(head, before, after);
    }

    private void recursion(Node curr,
                           Consumer<Integer> before, Consumer<Integer> after) { // 某个节点要进行的操作
        if (curr == null) {
            return;
        }

        before.accept(curr.value);
        recursion(curr.next, before, after);
        after.accept(curr.value);
    }

    private Node findLast() {
        if (head == null) { // 空链表
            return null;
        }

        Node p;
        for (p = head; p.next != null; p = p.next) {

        }
        return p;
    }

    /**
     * 向链表尾部添加
     *
     * @param value 待添加值
     */
    public void addLast(int value) {
        Node last = findLast();
        if (last == null) { // 空链表
            addFirst(value);
            return;
        }

        last.next = new Node(value, null);
    }

    private Node findNode(int index) {
        int i = 0;
        for (Node p = head; p != null; p = p.next, i++) {
            if (i == index) {
                return p;
            }
        }
        return null; // 没找到
    }

    /**
     * 根据索引查找
     *
     * @param index 索引
     * @return 找到, 返回该索引位置节点的值
     * @throws IllegalArgumentException 找不到, 抛出 index 非法异常
     */
    public int get(int index) throws IllegalArgumentException {
        Node node = findNode(index);
        if (node == null) {
            throw illegalIndex(index);
        }
        return node.value;
    }

    private IllegalArgumentException illegalIndex(int index) {
        // %d 占位符，表示数字
        return new IllegalArgumentException(String.format("index [%d] 不合法%n", index));
    }

    /**
     * 向索引位置插入
     *
     * @param index 索引
     * @param value 待插入值
     * @throws IllegalArgumentException 找不到, 抛出 index 非法异常
     */
    public void insert(int index, int value) throws IllegalArgumentException {
        if (index == 0) {
            addFirst(value);
            return;
        }
        Node prev = findNode(index - 1); // 找到上一个节点
        if (prev == null) { // 找不到
            throw illegalIndex(index);
        }
        prev.next = new Node(value, prev.next);
    }

    /**
     * 删除第一个
     *
     * @throws IllegalArgumentException - 如果不存在, 抛出 index 非法异常
     */
    public void removeFirst() throws IllegalArgumentException {
        if (head == null) {
            throw illegalIndex(0);
        }
        head = head.next;
    }

    /**
     * 从索引位置删除
     *
     * @param index 索引
     * @throws IllegalArgumentException 找不到, 抛出 index 非法异常
     */
    public void remove(int index) throws IllegalArgumentException {
        if (index == 0) {
            removeFirst();
            return;
        }

        Node prev = findNode(index - 1); // 上一个节点
        if (prev == null) {
            throw illegalIndex(index);
        }
        Node removed = prev.next; // 被删除的节点
        if (removed == null) {
            throw illegalIndex(index);
        }
        prev.next = removed.next;
    }
}













