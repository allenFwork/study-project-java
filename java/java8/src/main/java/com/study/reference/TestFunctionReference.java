package com.study.reference;

import java.util.function.Function;

public class TestFunctionReference {

    public static void main(String[] args) {
        Function<Long, Long> function = Math::abs;
        Long result = function.apply(-3L);
//        Integer result = function.apply(-3L);
        System.out.println(result);
    }

}
