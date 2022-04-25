package com.study.function_programme;

import java.util.function.Consumer;

/**
 * 使用Lambda表达式创建Consumer: 对象类型操作
 */
public class ConsumerLambda2 {

    static class Citizen {
        private String name;
        private int age;

        public Citizen(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    public static void main(String[] args) {

        /**
         * 设置实现功能:
         * 决定并显示一个公民在选举中是否可以投票的数据。
         */
        Consumer<Citizen> electionConsumer = x -> {
            if (x.getAge() < 18)
                System.out.println(x.getName() + " is not eligible to vote.");
            else
                System.out.println(x.getName() + " can vote.");
        };

        electionConsumer.accept(new Citizen("Jack", 16));
        electionConsumer.accept(new Citizen("Jerry", 20));

    }

}
