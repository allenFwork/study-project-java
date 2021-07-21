package com.study.lambda;

public class LambdaDemo1 {

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }

    public static void main(String[] args) {
        LambdaDemo1 lambdaDemo1 = new LambdaDemo1();

        /*------------------------------- 同一个接口的同一个方法的不同实现功能（开始） ------------------------------*/
        /*
         * (int x, int y) -> x + y  接收2个int型整数, 返回他们的和
         * MathOperation接口中只能有一个抽象方法，且该方法必须需要有两个int型参数，且该方法必须要有返回值
         */
        MathOperation addition = (int a, int b) -> a + b;

        /*
         * (x, y) -> x – y   接受2个参数(数字),并返回他们的差值
         */
        MathOperation subtraction = (a, b) -> a - b;

        MathOperation multiplication = (int a, int b) -> { return a * b; };//大括号后必须加上“;”

        MathOperation division = (int a, int b) -> a / b;

        // 调用方法
        System.out.println("10 + 5 = " + lambdaDemo1.operate(10, 5, addition) );
        System.out.println("10 - 5 = " + lambdaDemo1.operate(10, 5, subtraction));
        System.out.println("10 * 5 = " + lambdaDemo1.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + lambdaDemo1.operate(10, 5, division));
        /*------------------------------- 同一个接口的同一个方法的不同实现功能（结束） ------------------------------*/

        GreetingService greetingService1 =  message -> System.out.println("Hello " + message);
        GreetingService greetingService2 = (String message) -> System.out.println("Hello " + message);
        // GreetingService greetingService2 = (message) -> System.out.println("Hello " + message);

        greetingService1.sayMessage("superman");
        greetingService2.sayMessage("batman");

    }

}
