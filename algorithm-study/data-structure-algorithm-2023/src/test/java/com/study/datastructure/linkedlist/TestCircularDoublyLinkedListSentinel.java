package com.study.datastructure.linkedlist;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestCircularCircularDoublyLinkedListSentinel {

    @Test
    void addFirst() {
        CircularDoublyLinkedListSentinel list = new CircularDoublyLinkedListSentinel();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);

        assertIterableEquals(List.of(4, 3, 2, 1), list);
    }

    @Test
    void removeFirst() {
        CircularDoublyLinkedListSentinel list = getList();
        list.removeFirst();
        assertIterableEquals(List.of(2, 3, 4), list);
        list.removeFirst();
        assertIterableEquals(List.of(3, 4), list);
        list.removeFirst();
        assertIterableEquals(List.of(4), list);
        list.removeFirst();
        assertIterableEquals(List.of(), list);
        list.removeFirst();
        assertThrows(IllegalArgumentException.class, list::removeFirst);
    }

    @Test
    void addLast() {
        CircularDoublyLinkedListSentinel list = getList();
        assertIterableEquals(List.of(1, 2, 3, 4), list);
    }

    private CircularDoublyLinkedListSentinel getList() {
        CircularDoublyLinkedListSentinel list = new CircularDoublyLinkedListSentinel();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        return list;
    }

    @Test
    void removeLast() {
        CircularDoublyLinkedListSentinel list = getList();
        list.removeLast();
        assertIterableEquals(List.of(1, 2, 3), list);
        list.removeLast();
        assertIterableEquals(List.of(1, 2), list);
        list.removeLast();
        assertIterableEquals(List.of(1), list);
        list.removeLast();
        assertIterableEquals(List.of(), list);
        assertThrows(IllegalArgumentException.class, list::removeLast);
    }

    @Test
    void removeByValueTest() {
        CircularDoublyLinkedListSentinel list = new CircularDoublyLinkedListSentinel();
        list.addLast(1);
        list.addLast(2);
        list.addLast(2);
        list.addLast(3);
        list.addLast(3);
        list.addLast(3);
        list.addLast(4);

        list.removeByValue(1);
        assertIterableEquals(List.of(2, 2, 3, 3, 3, 4), list);
        list.removeByValue(2);
        assertIterableEquals(List.of(3, 3, 3, 4), list);
        list.removeByValue(4);
        assertIterableEquals(List.of(3, 3, 3), list);
    }
}