package com.study.function_programme;

import java.util.function.Consumer;

/**
 * Java Consumer接口来自Java 8中引入的 java.util.function包。
 * Consumer是一个功能接口，用来作为lambda表达式或方法引用的任务目标(传递一个参数执行指定的方法)。
 * Consumer的功能接口是一个接受单一参数并且不返回任何结果的操作。
 * Consumer的功能方法是accept(T t)。
 *
 * Consumer具有以下方法:
 * 1. accept : 这是Consumer功能接口的功能方法。accept 方法对给定的参数进行这一操作。
 * 2. andThen: 此方法返回一个组合的Consumer，该Consumer先执行原始的Consumer操作，然后按照从左到右的顺序执行给定的andThen操作。
 */
public class ConsumerDemo {

    public static void main(String[] args) {
        // 基本使用方法：定义具体accept方法中具体实现的功能 String类型的参数s
        Consumer<String> nameConsumer = s -> System.out.println(s);
        nameConsumer.accept("superman");
        nameConsumer.accept("batman");
    }

}
