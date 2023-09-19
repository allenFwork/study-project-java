package com.study.io.serialization;

import java.io.Serializable;

/**
 * 用于测试序列化的实体对象
 * 1.如果不实现java.io.Serializable接口库，那么会报 java.io.NotSerializableException: com.study.io.serialization.Entity 错误
 * 2. Serializable 是一个标记接口
 */
// public class Entity { // 测试
public class Entity implements Serializable {
    private String name;
    private Integer age;
    private transient String phoneNumber; // transient瞬态修饰成员,不会被序列化

    /**
     * 在生成完序列化文件后，新增了以下的address属性，测试反序列化时是否出现问题
     * 会报以下错误：
     * java.io.InvalidClassException: com.study.io.serialization.Entity;
     * local class incompatible: stream classdesc serialVersionUID = -1740072866703248893,
     * local class serialVersionUID = 2272719698745039098
     */
    private String address;

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
