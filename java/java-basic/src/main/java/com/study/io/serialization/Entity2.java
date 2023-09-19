package com.study.io.serialization;

import java.io.Serializable;

/**
 * 用于测试序列化的实体对象
 * 1.如果不实现java.io.Serializable接口库，那么会报 java.io.NotSerializableException: com.study.io.serialization.Entity 错误
 * 2. Serializable 是一个标记接口
 */
// public class Entity { // 测试
public class Entity2 implements Serializable {

    // 加入序列版本号
    private static final long serialVersionUID = 1L;

    private String name;
    private Integer age;
    private transient String phoneNumber; // transient瞬态修饰成员,不会被序列化

    private String address; // 添加新的属性,重新编译, 可以反序列化,该属性赋为默认值.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void method1() {
        System.out.println("method1 ... name = " + name + ", age = " + age);
    }

    public static void method2(String name, Integer age) {
        System.out.println("method2 ... name = " + name + ", age = " + age);
    }

}
