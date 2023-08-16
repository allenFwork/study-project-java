package com.study.function_programme2.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Stream的高级用法：
 * 1.基本数据类型优化
 * 2.并行流处理
 */
public class StreamMainClass2 {

    public static void main(String[] args) {

//        List<Author> authors = StreamMainClass.getAuthors();
//        // 基本数据类型优化
//        authors.stream()
//                .map(author -> author.getAge())
//                .map(age -> age + 10)
//                .filter(age -> age > 18)
//                .map(age -> age + 2)
//                .forEach(System.out::println);
//
//        authors.stream()
//                .mapToInt(author -> author.getAge()) // 基本数据类型优化,从这行代码向下都是int类型的基本数据操作，不会再进行 Integer与int类型的自动转化了
//                .map(age -> age + 10)
//                .filter(age -> age > 18)
//                .map(age -> age + 2)
//                .forEach(System.out::println);

        // 并行流处理
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        Stream<Integer> streamInt = list.stream();
        Long start = System.currentTimeMillis();
        Integer sum = streamInt.filter(num -> num > 5).reduce((result, element) -> result + element).get();
        Long end = System.currentTimeMillis();
        System.out.println("串行处理时间为：" + (end - start) + "毫秒");

        streamInt = list.stream();
        start = System.currentTimeMillis();
        sum = streamInt.parallel() // 并行流处理(方法1)
                .filter(num -> num > 5).reduce((result, element) -> result + element).get();
        end = System.currentTimeMillis();
        System.out.println("并行1处理时间为：" + (end - start) + "毫秒");

        streamInt = list.parallelStream(); // 并行处理(方法2)
        start = System.currentTimeMillis();
        sum = streamInt.filter(num -> num > 5).reduce((result, element) -> result + element).get();
        end = System.currentTimeMillis();
        System.out.println("并行2处理时间为：" + (end - start) + "毫秒");

        /**
         * 处理结果：
         * 串行处理时间为：34毫秒
         * 并行1处理时间为：5毫秒
         * 并行2处理时间为：2毫秒
         */
    }
}
