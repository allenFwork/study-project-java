package com.study.service;

import com.study.entity.Student;

import java.util.List;

public interface MongoService {

    String insertOne();

    String findAll();

    Student findByCondition(String name, Integer age);

    List<Student> findByListCondition(List<String> teacherNameList);

}
