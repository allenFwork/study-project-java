package com.study.genericity.demo9;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainClass {

    public static void main(String[] args) {
//        Erasure<Integer> erasure = new Erasure<>();
//        // 利用反射: 获取当前Erasure类的字节码文件的Class类对象
//        Class<? extends Erasure> clz = erasure.getClass();
//        // 获取所有的成员变量
//        Field[] declaredFields = clz.getDeclaredFields();
//        for (Field field : declaredFields) {
//            // 打印所有成员变量的名称和类型
//            System.out.println(field.getName() + ": " + field.getType().getSimpleName());
//        }
//        // 打印结果：key: Object
//
//        // 利用反射获取所有的方法
//        Method[] methods = clz.getDeclaredMethods();
//        for (Method method : methods) {
//            // 打印方法名和方法的返回值类型
//            System.out.println(method.getName() + ": " + method.getReturnType().getSimpleName());
//        }
//        /**
//         * 打印结果：
//         * getKey: Number
//         * setKey: void
//         * showKey: List
//         */

        System.out.println("-----------------------------------------------------");
        InfoImpl info = new InfoImpl();
        Class infoClz = info.getClass();
        Method[] methods = infoClz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + ": " + method.getReturnType().getSimpleName());
        }
        /**
         * 打印结果：
         * info: Integer
         * info: Object
         */
    }

}
