package com.study.lambda;


public class LambdaDemo2 {

    final static String name = "superman";

    interface OutParameter {
        void printOutParameter(String name);
    }

    interface Converter<T1, T2> {
        void convert(int i);
    }

    public static void main(String[] args) {
        OutParameter outParameter = message -> System.out.println(name + message);
        outParameter.printOutParameter(", good morning!");

        final int num = 1;
        // num 没有被final修饰时， Error:(21, 93) java: 从lambda 表达式引用的本地变量必须是最终变量或实际上的最终变量
        Converter<Integer, String> s = (param) -> System.out.println(String.valueOf(param + num));
        s.convert(2);

    }
}
