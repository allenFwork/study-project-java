package com.study.io.serialization;

import java.io.*;

/**
 * java的io学习：序列化流 ObjectOutputStream 与反序列化流 ObjectInputStream
 */
public class SerializationDemo {

    // 序列化
    public static void objToBinary() {
        Entity entity = new Entity();
        entity.setName("超人");
        entity.setAge(18);
        entity.setPhoneNumber("123456789");

        // 创建序列化流对象
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\documents\\study\\test\\obj.txt"));) {
            // 写出对象
            oos.writeObject(entity);
            System.out.println("Serialized data is saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 反序列化
    public static void binaryToObj() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\documents\\study\\test\\obj.txt"));) {
            // 读取一个对象
            Entity entity = (Entity) ois.readObject();
            System.out.println("反序列化后得到的对象，name=" + entity.getName() + ", age = " + entity.getAge());
            entity.method1();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 序列化
    public static void objToBinary2() {
        Entity2 entity2 = new Entity2();
        entity2.setName("超人");
        entity2.setAge(18);
        entity2.setPhoneNumber("123456789");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        // 创建序列化流对象
        try {
            fos = new FileOutputStream("D:\\documents\\study\\test\\obj2.txt");
            oos = new ObjectOutputStream(fos);
            // 写出对象
            oos.writeObject(entity2);
            System.out.println("Serialized data is saved");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null)
                    oos.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 反序列化
    public static void binaryToObj2() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\documents\\study\\test\\obj2.txt"));) {
            // 读取一个对象
            Entity2 entity2 = (Entity2) ois.readObject();
            System.out.println("反序列化后得到的对象，name=" + entity2.getName() + ", age = " + entity2.getAge());
            entity2.method1();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        objToBinary();
//        binaryToObj();

        objToBinary2();
        binaryToObj2();
    }

}
