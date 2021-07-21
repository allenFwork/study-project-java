package com.study.optional;

import java.util.Optional;

public class Demo {

    public static void main(String[] args) {

    }

    private void test(String name) {

        Optional optional1 = Optional.of(name);
        Optional optional2 = Optional.ofNullable(name);
    }

}
