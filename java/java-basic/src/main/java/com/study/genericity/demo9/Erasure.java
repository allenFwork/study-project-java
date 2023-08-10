package com.study.genericity.demo9;

import java.util.List;

public class Erasure<T extends Number> {

    private T key;

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public <T extends List> T showKey(T t) {
        return t;
    }

}
