package com.study.common_api;

public class SystemDemo {

    // 计算耗时
    public static void computingTime() {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.printf("逻辑执行花费了%d毫秒", end - start);
        System.out.println();
    }

    // 停止JVM虚拟机
    public static void exit() {
        System.out.println("执行第一步 ... ");
        // 0表示正常退出，非零表示异常退出
        System.exit(0); // 退出了程序，终止当前正在运行的Java虚拟机，下面的代码不会执行
        System.out.println("执行第二步 ... ");
    }

    // 数组的复制1: 基本类型数组复制
    public static void arrayCopy() {
        int sourceArr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int destArr[] = new int[10];
        // 进行数组元素的copy: 把sourceArr数组中从2索引开始的5个元素，从destArr数组中的0索引开始复制过去
        System.arraycopy(sourceArr, 2, destArr, 0, 5);
        // 打印结果
        for (int i = 0; i < destArr.length; i++) {
            if (i != destArr.length - 1)
                System.out.print(destArr[i] + ", ");
            else
                System.out.print(destArr[i]);
        }
        System.out.println();
    }

    class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{name=" + name + "}";
        }
    }

    class Student extends Person {
        private String schoolName;

        public Student(String name, String schoolName) {
            super(name);
            this.schoolName = schoolName;
        }
    }

    // 数组的复制2：引用类型数组复制
    public void arrayCopy2() {
        Student studentArr[] = new Student[3];
        studentArr[0] = new Student("小明","1号学校");
        studentArr[1] = new Student("小红","1号学校");
        studentArr[2] = new Student("小兰","1号学校");
        Person personArr[] = new Person[3];
        System.arraycopy(studentArr, 0, personArr, 0, 3);
        for (Person person : personArr) {
            System.out.println(person);
        }
    }


    public static void main(String[] args) {
        computingTime();
        // exit();
        arrayCopy();
        // 非静态方法无法在静态方法中直接调用
        new SystemDemo().arrayCopy2();
    }

}
