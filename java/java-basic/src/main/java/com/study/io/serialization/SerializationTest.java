package com.study.io.serialization;

import java.io.*;
import java.util.ArrayList;

/**
 * 实现：
 * 1. 将存有多个自定义对象的集合序列化操作，保存到list.txt文件中。
 * 2. 反序列化list.txt ，并遍历集合，打印对象信息。
 */
public class SerializationTest {

    private static void serialize(ArrayList<Student> arrayList) throws Exception {
        // 创建 序列化流
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\documents\\study\\test\\list.txt"));
        // 写出对象
        oos.writeObject(arrayList);
        // 释放资源
        oos.close();
    }

    private static ArrayList<Student> reverseSerialize() throws Exception {
        // 创建 序列化流
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\documents\\study\\test\\list.txt"));
        // 写出对象
        ArrayList<Student> arrayList = (ArrayList<Student>) ois.readObject();
        // 释放资源
        ois.close();
        return arrayList;
    }

    public static void main(String[] args) throws Exception {

        Student student1 = new Student("张三", "北京");
        Student student2 = new Student("李四", "上海");
        Student student3 = new Student("王五", "深圳");
        ArrayList<Student> arrayList = new ArrayList<>();
        arrayList.add(student1);
        arrayList.add(student2);
        arrayList.add(student3);

        // 序列化操作
        serialize(arrayList);

        // 反序列化
        ArrayList<Student> students = reverseSerialize();
        for (Student student : students) {
            System.out.println(student.getName() + ", " + student.getAddress());
        }

    }

}

class Student implements Serializable {
    private String name;
    private String address;

    public Student(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
