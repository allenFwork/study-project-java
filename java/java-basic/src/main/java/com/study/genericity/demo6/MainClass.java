package com.study.genericity.demo6;

import com.study.genericity.demo3.ProductGetter;

import java.util.ArrayList;

/**
 * 泛型方法的调用使用
 */
public class MainClass {

    public static void main(String[] args) {

        ProductGetter<Integer> productGetter = new ProductGetter();
        int[] products = {100, 200, 400};
        for (int item : products) {
            productGetter.addProduct(item);
        }
        // 泛型类的成员方法调用,该泛型对应的类型在声明创建 productGetter 对象时, 已经确定为Integer了,不可更改
        Integer product = productGetter.getProduct();

        ArrayList<String> strList = new ArrayList<>();
        strList.add("笔记本电脑");
        strList.add("苹果手机");
        strList.add("扫地机器人");
        // 调用泛型方法,类型是在调用方法的时候指定的
        String product1 = productGetter.getProduct(strList);
        System.out.println(product1 + "\t" + product1.getClass().getSimpleName()); // 正确，没有问题
        ArrayList<Integer> intList = new ArrayList<>();
        intList.add(100);
        intList.add(200);
        intList.add(300);
        // 调用泛型方法,类型是在调用方法的时候指定的
        Integer product2 = productGetter.getProduct(intList);
        System.out.println(product2 + "\t" + product2.getClass().getSimpleName()); // 正确，没有问题

        // 调用具有多个泛型类型参数的静态泛型方法
        ProductGetter.printType(100, "java", true);

        // 泛型可变方法的调用
        ProductGetter.print(1, 2, 3, 4, 5);
        ProductGetter.print("a", "b", "c");

    }

}
