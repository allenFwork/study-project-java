package com.study.function_programme2.functionalInterface;

import java.util.function.*;

/*
 * 函数式接口
 * 使用 @FunctionalInterface 修饰的接口叫做函数式接口
 * @FunctionalInterface 起到校验作用
 *
 * jdk8中增加了许多函数式接口，比如 java.util.function包下的几个接口：
 *      Supplier
 *      Function
 *      Consumer
 *      Predicate
 */
@FunctionalInterface
public interface CustomizedFunctionInterface {

    // 只能定义一个抽象方法，才能作为函数直接扣
    void test();

}
