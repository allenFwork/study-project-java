package com.study;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import org.junit.Test;
import org.springframework.util.Assert;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 序列化与反序列化的测试
 */
public class HessianTest {

    public enum Sex {
        man, woman
    }

    public static class CustomizedList extends ArrayList {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class User implements Serializable {
        private String name;
        private Date date;
        private Long age;
        private Sex sex;
        List<String> list;

        public User() {

        }

        public User(String name, Date date, Long age) {
            this.name = name;
            this.date = date;
            this.age = age;
        }

        public void setSex(Sex sex) {
            this.sex = sex;
        }

        public void setList(List list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", date=" + date +
                    ", age=" + age +
                    ", sex=" + sex +
                    ", list=" + list +
                    '}';
        }
    }

    /*--------------------------- java原生序列化与反序列化（开始）---------------------------*/
    @Test
    public void javaWriteTest() throws IOException {
        String filePath = System.getProperty("user.dir") + "/target/user_java";
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath));
        User user = new User("superman", new Date(), 18l);
        user.setSex(Sex.man);
        // 将对象写出到文件中(序列化)
        outputStream.writeObject(user);
    }

    @Test
    public void javaReadTest() throws IOException, ClassNotFoundException {
        String filePath = System.getProperty("user.dir") + "/target/user_java";
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath));
        User user = (User) inputStream.readObject();
        Assert.notNull(user, "user must be not null");
    }
    /*----------------------------- java原生序列化与反序列化（结束）----------------------------*/


    /*----------------------------- Hessian框架序列化与反序列化（开始）----------------------------*/
    @Test
    public void hessianWriteTest() throws IOException {
        String filePath = System.getProperty("user.dir") + "/target/user_hessian";
        Hessian2Output hessian2Output = new Hessian2Output(new FileOutputStream(filePath));
        User user = new User("superman", new Date(), 18l);
        user.setSex(Sex.man);
        CustomizedList list = new CustomizedList();
        list.add("abc");
        user.setList(list);
        // 将对象写出到文件中(序列化)
        hessian2Output.writeObject(user);
        hessian2Output.flush();
    }

    @Test
    public void hessianReadTest() throws IOException, ClassNotFoundException {
        String filePath = System.getProperty("user.dir") + "/target/user_hessian";
        Hessian2Input hessian2Input = new Hessian2Input(new FileInputStream(filePath));
        User user = (User) hessian2Input.readObject();
        System.out.println(user);
    }
    /*----------------------------- Hessian框架序列化与反序列化（结束）----------------------------*/

}
