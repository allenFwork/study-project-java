package com.study.function_programme2;

import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

/**
 * lambda学习一：从匿名内部类转化为lambda表达式练习
 */
public class LambdaDemo1 {

    public static void main(String[] args) {

        // 使用匿名内部类作为参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程启动了，开始执行任务 ... ");
            }
        }).start();

        /**
         * 使用Lambda表达式改进：
         *      1.不关心Runnable类型，也不关系run的方法名
         *      2.只关注run方法的参数和方法体
         *      所以可以写成 () -> {方法体内容}
         */
        new Thread(() -> {
            System.out.println("线程启动了，开始执行任务 ... ");
        }).start();
        new Thread(() -> System.out.println("线程启动了，开始执行任务 ... ")).start();

        // 使用匿名内部类方式
        int i = calculateNum(new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left + right;
            }
        });
        // 使用Lambda表达式
        i = calculateNum((int left, int right) -> {
            return left + right;
        });
        System.out.println(i);
        printNum(new IntPredicate() {
            @Override
            public boolean test(int value) {
                return value % 2 == 0;
            }
        });

        Integer r = typeCover(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.valueOf(s);
            }
        });
        r = typeCover((String s) -> {
            return Integer.valueOf(s);
        });
        System.out.println(r + ": " + r.getClass().getSimpleName());

        foreachArr(new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value + "处理中 。。。");
            }
        });
        foreachArr((int value) -> {
            System.out.println(value + "处理中 。。。 ");
        });

    }

    public static int calculateNum(IntBinaryOperator operator) {
        int a = 10;
        int b = 20;
        return operator.applyAsInt(a, b);
    }

    public static void printNum(IntPredicate predicate) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i : arr) {
            if (predicate.test(i)) {
                System.out.println(i);
            }
        }
    }

    public static <R> R typeCover(Function<String, R> function) {
        String str = "12345";
        R result = function.apply(str);
        return result;
    }

    public static void foreachArr(IntConsumer consumer) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i : arr) {
            consumer.accept(i);
        }
    }

}
