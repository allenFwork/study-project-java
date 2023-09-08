package com.study.common_api;

import java.util.Objects;
import java.util.function.Supplier;

public class ObjectsDemo {

    public static void basicMethod() {
        Student student1 = new Student("张三", "18");
        Student student2 = new Student("李四", "20");

        // 1.测试nonNull方法
        boolean nonNull = Objects.nonNull(student1);
        System.out.println(nonNull);//true

        // 2.测试isNull方法
        boolean isNull = Objects.isNull(student1);
        System.out.println(isNull);//false

        // 3.测试equals方法：调用Objects类中的equals方法，比较两个对象是否相等
        boolean isEqual = Objects.equals(student1, student2);// 如果Student没有重写Object类中的equals方法，此处比较的还是对象的地址值
        System.out.println(isEqual);//false

        // 4.测试toString方法：用Objects中的toString方法,获取s1对象的字符串表现形式
        String str = Objects.toString(student1); // 如果Student没有重写Object类中的toString方法，此处还是返回的对象的地址值
        System.out.println(str);        //Student{name='张三', age='18'}
        System.out.println(student1);   //Student{name='张三', age='18'}
    }

    public static void specialMethod() {
        Student student1 = new Student("张三", "18");
        Student student = Objects.requireNonNull(student1);
        System.out.println(student);
        student = Objects.requireNonNull(null);
        System.out.println(student);
    }

    public static void main(String[] args) {
        // basicMethod();
        specialMethod();
    }
}

class Student {
    private String name;
    private String age;

    public Student() {
    }

    public Student(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) &&
                Objects.equals(age, student.age);
    }

    @Override
    public int hashCode() {
        return 0;
    }

}