package com.study.genericity.demo9;

public class InfoImpl implements Info<Integer> {
    @Override
    public Integer info(Integer value) {
        return value;
    }
}
