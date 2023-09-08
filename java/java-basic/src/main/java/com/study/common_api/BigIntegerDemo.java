package com.study.common_api;

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerDemo {

    // 创建BigInteger对象：构造方法+静态方法使用
    public static void constructMethod() {

        // 1.获取一个随机的大整数: public BigInteger(int num, Random rnd) 获取随机大整数，范围：[0 ~ 2的num次方-1]
        Random random = new Random();
        BigInteger bigInteger1 = new BigInteger(4, random); //范围：[0 ~ 15]
        System.out.println(bigInteger1);

        // 2.获取一个指定的大整数：public BigInteger(String val)
        BigInteger bigInteger2 = new BigInteger("9999999999999999999999999999999");
        System.out.println(bigInteger2);
        // bigInteger2 = new BigInteger("1.1");   报错： java.lang.NumberFormatException: For input string: "1.1"
        // bigInteger2 = new BigInteger("abc");   报错：java.lang.NumberFormatException: For input string: "abc"

        // 3.获取指定进制的大整数：public BigInteger(String val, int radix)
        BigInteger bigInteger3 = new BigInteger("123", 10); //该字符串是10进制的，将其结果转化为十进制的大整数
        System.out.println(bigInteger3); //123
        bigInteger3 = new BigInteger("123", 8);             //该字符串是8进制的，将其结果转化为十进制的大整数
        System.out.println(bigInteger3); //83
        bigInteger3 = new BigInteger("123", 4);             //该字符串是4进制的，将其结果转化为十进制的大整数
        System.out.println(bigInteger3); //27
        bigInteger3 = new BigInteger("01111111", 2);        //该字符串是2进制的，将其结果转化为十进制的大整数
        System.out.println(bigInteger3); //127

        // 4.静态方法获取BigInteger对象，内部有优化
        // 在内部对常用的数字：-16 ~ 16 进行了优化，提前把 -16 ~ 16 创建好BigInteger对象，如果多次获取不会重新创建新的
        BigInteger bigInteger4 = BigInteger.valueOf(100);
        System.out.println(bigInteger4);//100
        BigInteger bigInteger5 = BigInteger.valueOf(16);
        BigInteger bigInteger6 = BigInteger.valueOf(16);
        System.out.println(bigInteger5 == bigInteger6); //true
        BigInteger bigInteger7 = BigInteger.valueOf(17);
        BigInteger bigInteger8 = BigInteger.valueOf(17);
        System.out.println(bigInteger7 == bigInteger8); //false

        // 5.对象一旦创建内部的数据就不能发生改变
        BigInteger bigInteger9 = BigInteger.valueOf(1);
        BigInteger bigInteger10 = BigInteger.valueOf(2);
        //此时，不会修改参与计算的BigInteger对象中的值，而是产生了一个新的BigInteger对象记录
        BigInteger result = bigInteger9.add(bigInteger10);
        System.out.println(result);//3
        System.out.println(bigInteger9 == result); //false
        System.out.println(bigInteger10 == result);//false
    }

    // 基础的方法
    public static void basicMethod() {

        BigInteger bigInteger1 = BigInteger.valueOf(10);
        BigInteger bigInteger2 = BigInteger.valueOf(3);

        // 加法
        BigInteger result = bigInteger1.add(bigInteger2);
        System.out.println(result);//13

        // 减法
        result = bigInteger1.subtract(bigInteger2);
        System.out.println(result);//7

        // 乘法
        result = bigInteger1.multiply(bigInteger2);
        System.out.println(result);//30

        // 除法
        result = bigInteger1.divide(bigInteger2);
        System.out.println(result);//3

        // 除法，求出商和余数，返回的数组中第一个元素：商，第二个元素：余数
        BigInteger[] resultArr = bigInteger1.divideAndRemainder(bigInteger2);
        System.out.println(resultArr[0]);//3
        System.out.println(resultArr[1]);//1

        // 次冥，例如下面求 10的3次方
        result = bigInteger1.pow(3);
        System.out.println(result);//1000

        // 取较大的BigInteger对象
        result = bigInteger1.max(bigInteger2);
        System.out.println(result);//10

    }

    public static void main(String[] args) {
        // constructMethod();
        basicMethod();
    }

}
