package com.study.Stream;

import java.util.ArrayList;
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

        List<String> strings2 = new ArrayList<>();
        strings2.add("abc");
        strings2.add("");
        strings2.add("def");
        strings2.add("g");
        strings2.add("hi");
        strings2.add("jkl");
        List<String> filters2 = strings2.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        strings2.clear();
        System.out.println(filters2);

        Random random = new Random();

    }

}
