package com.study.Stream;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class StreamDemo1 {


    public static void main(String[] args) {

        /*
         * 在 Java 8 中, 集合接口有两个方法来生成流：
         *    stream() − 为集合创建串行流。
         *    parallelStream() − 为集合创建并行流。
         */

        List<String> strings = Arrays.asList("abc", "", "def", "g", "hi", "jkl");
        List<String> filters = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println(strings);
        System.out.println(filters);

        Random random = new Random();

    }

}
