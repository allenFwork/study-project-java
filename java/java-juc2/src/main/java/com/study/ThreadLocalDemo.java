package com.study;

public class ThreadLocalDemo {

    // 测试实体对象
    static class Student {

        private String name;
        private Integer age;

        public Student(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

    }

    public static void main(String[] args) {
        ThreadLocal<Student> threadLocal = new ThreadLocal<Student>();
        Thread mainThread = Thread.currentThread();
        Student student1 = new Student("student1", 18);
        threadLocal.set(student1);
        System.out.println("主线程||获取当前ThreadLocal对象中存储的数据： " + threadLocal.get());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Student student2 = new Student("student2", 19);
                threadLocal.set(student2);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程1||获取当前ThreadLocal对象中存储的数据： " + threadLocal.get());
                student2 = new Student("student2.2", 19);
                threadLocal.set(student2);
                System.out.println("线程1||获取当前ThreadLocal对象中存储的数据： " + threadLocal.get());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Student student3 = new Student("student3", 20);
                threadLocal.set(student3);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程2||获取当前ThreadLocal对象中存储的数据： " + threadLocal.get());
            }
        }).start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程||获取当前ThreadLocal对象中存储的数据： " + threadLocal.get());
    }

}
