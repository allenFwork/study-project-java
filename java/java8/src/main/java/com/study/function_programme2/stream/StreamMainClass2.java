package com.study.function_programme2.stream;

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
        Stream<Integer> streamInt = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // Stream<Integer> streamInt = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        Integer sum = streamInt.filter(num -> num > 5).reduce((result, element) -> result + element).get();
        System.out.println(sum);
        sum = streamInt.parallel() // 并行流处理
                .filter(num -> num > 5).reduce((result, element) -> result + element).get();
        System.out.println(sum);
    }
}
