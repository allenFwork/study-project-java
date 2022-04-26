package com.study.first_chapter.content;

import java.util.Collection;

public class Demo2 {

    public static void main(java.lang.String[] args) {
        Object[] arr = new Integer[10];
        arr[0] = new Integer(1);
        // Exception in thread "main" java.lang.ArrayStoreException: java.lang.Double
        arr[1] = new Double(1.0);

    }

    abstract class Shape {

        public abstract double area();

        // 传入类型的参数必须是Shape类型,即使子类型也不行,依旧报错
        public abstract double totalArea(Collection<Shape> shapes);

        // 通过泛型机制实现传入参数类型的变化
        public abstract double totalArea2(Collection<? extends Shape> shapes);

    }

    class Square extends Shape {

        private double height;
        private double weight;

        @Override
        public double area() {
            return height * weight;
        }

        @Override
        public double totalArea(Collection<Shape> shapes) {
            return 0;
        }

        @Override
        public double totalArea2(Collection<? extends Shape> shapes) {
            return 0;
        }

    }

    class Circular extends Shape {

        @Override
        public double area() {
            return 0;
        }

        @Override
        public double totalArea(Collection<Shape> shapes) {
            return 0;
        }

        @Override
        public double totalArea2(Collection<? extends Shape> shapes) {
            return 0;
        }

    }

}
