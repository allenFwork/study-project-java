package com.study.common_api;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalDemo {

    public static void basicMethod() {
        // 1.创建 BigDecimal对象
        BigDecimal bigDecimal1 = new BigDecimal("0.009");
        BigDecimal bigDecimal2 = new BigDecimal("0.001");
        BigDecimal bigDecimal3 = new BigDecimal("4");

        // 2.四则运算
        System.out.println(bigDecimal1.add(bigDecimal2));//0.010
        System.out.println(bigDecimal1.subtract(bigDecimal2));//0.008
        System.out.println(bigDecimal1.multiply(bigDecimal3));//0.036
        System.out.println(bigDecimal1.divide(bigDecimal2));//9

        // 3.除法调用时出现问题
        BigDecimal b1 = new BigDecimal("1");
        BigDecimal b2 = new BigDecimal("3");
        // 报错：java.lang.ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result.
        System.out.println(b1.divide(b2));
    }

    // 除法方法详解
    public static void divideMethod() {

        BigDecimal b1 = new BigDecimal("1");
        BigDecimal b2 = new BigDecimal("3");

        // 调用方法进行b1和b2的除法运算，保留两位有效数字精度，HALF_UP：四舍五入模式
        System.out.println(b1.divide(b2, 2, RoundingMode.HALF_UP));//0.33

        // 调用方法进行b1和b2的除法运算，保留两位有效数字精度， FLOOR：直接删除
        System.out.println(b1.divide(b2, 2, RoundingMode.FLOOR));//0.33

        // 调用方法进行b1和b2的除法运算，保留两位有效数字精度，UP：直接进1
        System.out.println(b1.divide(b2, 2, RoundingMode.UP));//0.34

    }

    public static void main(String[] args) {
        // 打印结果会丢失精度
        System.out.println("java 中 0.009 + 0.001 = " + (0.09 + 0.01)); // java 中 0.009 + 0.001 = 0.09999999999999999

        // basicMethod();

        divideMethod();
    }


}
