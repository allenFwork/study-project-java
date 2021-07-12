package com.study.generic;

/**
 * 泛型-模拟栈
 */
public class Stack {

    private int[] data = new int[10];

    private int i = 0;

    public void push(int object) {
        data[i++] = object;
    }

    public int pop() {
        return data[--i];
    }

}
