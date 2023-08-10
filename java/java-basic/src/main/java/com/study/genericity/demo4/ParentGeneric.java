package com.study.genericity.demo4;

public class ParentGeneric<E> {

    private E value;

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

}
