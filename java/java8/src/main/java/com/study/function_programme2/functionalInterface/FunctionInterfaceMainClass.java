package com.study.function_programme2.functionalInterface;

import com.study.function_programme2.stream.Author;
import com.study.function_programme2.stream.StreamMainClass;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * 函数式编程测试
 */
public class FunctionInterfaceMainClass {

    public static void main(String[] args) {
        testAnd();
        printNum(value -> value % 2 == 0, value -> value > 4);
        testOr();
        testNegate();
    }

    // 打印作家年龄大于17，并且姓名的长度大于1的作家
    private static void testAnd() {
        List<Author> authors = StreamMainClass.getAuthors();
        // 一般不会这么使用 and方法，一般用于自定义的方法中使用and方法
        authors.stream().filter(new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.getAge() > 17;
            }
        }.and(new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.getName().length() > 1;
            }
        })).forEach(author -> System.out.println("姓名：" + author.getName() + ", 年龄：" + author.getAge()));
    }

    // 传入两个判断条件操作
    public static void printNum(IntPredicate predicate1, IntPredicate predicate2) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i : arr) {
            // 两个判断条件通过and方法连接起来，表示并且操作
            if (predicate1.and(predicate2).test(i)) {
                System.out.println(i);
            }
        }
    }

    // 打印作家年龄大于17 或者 姓名的长度小于2的作家
    private static void testOr() {
        List<Author> authors = StreamMainClass.getAuthors();
        authors.stream().filter(new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.getAge() > 17;
            }
        }.or(new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.getName().length() < 2;
            }
        })).forEach(author -> System.out.println("姓名：" + author.getName() + ", 年龄：" + author.getAge()));
    }

    // 打印作家中年龄不大于17的作家
    private static void testNegate() {
        List<Author> authors = StreamMainClass.getAuthors();
        authors.stream().filter(new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.getAge() > 17;
            }
        }.negate()).forEach(author -> System.out.println("姓名：" + author.getName() + ", 年龄：" + author.getAge()));
    }

}
