package com.study.genericity.demo7;

/**
 *
 * @param <T>
 */
public class Box<T> {

    private T first;

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

}
