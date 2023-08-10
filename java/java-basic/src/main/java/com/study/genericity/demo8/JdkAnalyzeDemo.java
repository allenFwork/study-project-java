package com.study.genericity.demo8;

import java.util.Comparator;
import java.util.TreeSet;

public class JdkAnalyzeDemo {

    public static void main(String[] args) {

        TreeSet<Cat> cats1 = new TreeSet<>(new Comparator1());

        TreeSet<Cat> cats2 = new TreeSet<>(new Comparator1());
//        TreeSet<Cat> cats2 = new TreeSet<>(new Comparator2());
        cats2.add(new Cat("Jerry1", 30));
        cats2.add(new Cat("Jerry2", 20));
        cats2.add(new Cat("Jerry3", 10));
        cats2.add(new Cat("Jerry4", 0));
        for (Cat cat : cats2) {
            System.out.println(cat);
        }

         // 下面这行代码，编译错误: 因为 new TreeSet<>(Comparator<? super E>) 限定了下限
         // TreeSet<Cat> cats3 = new TreeSet<Cat>(new Comparator3());
    }

    static class Comparator1 implements Comparator<Animal> {
        @Override
        public int compare(Animal o1, Animal o2) {
            return o1.name.compareTo(o2.name);
        }
    }

    static class Comparator2 implements Comparator<Cat> {
        @Override
        public int compare(Cat o1, Cat o2) {
            return o1.age - o2.age;
        }
    }

    static class Comparator3 implements Comparator<MiniCat> {
        @Override
        public int compare(MiniCat o1, MiniCat o2) {
            return o1.level - o2.level;
        }
    }

}
