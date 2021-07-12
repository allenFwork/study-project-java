package com.study.generic;

public class Bean<T> {

    private T var;

    public T getVar() {
        return var;
    }

    public void setVar(T var) {
        this.var = var;
    }

    public String toString() {
        return var.toString();
    }
}
