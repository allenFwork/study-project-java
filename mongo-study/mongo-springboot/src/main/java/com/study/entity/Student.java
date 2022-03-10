package com.study.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通过 org.springframework.data.mongodb.core.mapping.Document 注解将 类映射到mongo数据库的集合上
 * 如果不设置@Document("mongo_student"), 那么会默认映射到 mongo中的 student的集合中
 * 设置了, 就会映射到 mongo_student 集合中
 */
@Document("test_student")
@Data
public class Student implements Serializable {

    /**
     * serialVersionUID是在类进行反序列化的时候,用于判断是不是这个这个类的
     * 比如 Test.class 进行序列化后，生成了文件 test.txt
     * 此时 Test.class文件进行修改，删除了其中一个属性, 但是包名、类名没有改变，
     * 如果没有设置 serialVersionUID, 那么此时就会认为 test.txt反序列化为 Test.class没有问题，进行反序列化
     * 但是如果设置了serialVersionUID, 那么此时会通过包名、类名、属性、serialVersionUID进行对比，确认 test.txt是不是能够反序列化为 Test.class
      */
    private static final long serialVersionUID = 2814767645606170035L;

    private String name;
    private Integer age;
    private List<Double> scoreList;
    private Map<String, Date> subjects;
    private Teacher teacher;

    public static class Teacher implements Serializable {

        private static final long serialVersionUID = 2814767645696170035L;

        private String name;

        public Teacher(String name) {
            this.name = name;
        }

    }

}
