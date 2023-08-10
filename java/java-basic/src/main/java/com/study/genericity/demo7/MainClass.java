package com.study.genericity.demo7;

public class MainClass {

    public static void main(String[] args) {
        Box<Number> box = new Box();
        box.setFirst(100);
        showBox(box);

        Box<Integer> box2 = new Box();
        box.setFirst(100);
        showBox(box2);
    }

//    public static void showBox(Box<Number> box) {
//        Number first = box.getFirst();
//        System.out.println(first);
//    }

    // 适用类型通配符?作为泛型的实参(基本使用)
//    public static void showBox(Box<?> box) {
//        Object first = box.getFirst();
//        System.out.println(first);
//    }

    // 适用类型通配符?作为泛型的实参(类型通配符的上限)
    public static void showBox(Box<? extends Number> box) {
        Number first = box.getFirst();
        System.out.println(first);
    }

    // 下面两个方法并能作为方法的重载，因为泛型中 Box<Number> 和 Box<Integer 实质就是一个类型
//    public static void showBox(Box<Number> box);
//    public static void showBox(Box<Integer> box);

}
