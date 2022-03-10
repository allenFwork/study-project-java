package com.study.service.impl;

import com.study.entity.Student;
import com.study.mongo1.dao.Student1Dao;
import com.study.mongo1.entity.Student1Entity;
import com.study.mongo2.dao.Student2Dao;
import com.study.mongo2.entity.Student2Entity;
import com.study.service.DifferentMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DifferentMongoServiceImpl implements DifferentMongoService {

    @Autowired
    private Student1Dao student1Dao;

    @Autowired
    private Student2Dao student2Dao;

    @Override
    public String insertOne() {
        Student1Entity student1Entity = new Student1Entity();
        student1Entity.setName("student1");
        student1Entity.setAge(20);
        List scoreList = new ArrayList();
        scoreList.add(60);
        scoreList.add(70);
        student1Entity.setScoreList(scoreList);
        student1Dao.insertOne(student1Entity);

        Student2Entity student2Entity = new Student2Entity();
        student2Entity.setName("student1");
        student2Entity.setAge(20);
        student2Entity.setScoreList(scoreList);
        student2Dao.insertOne(student2Entity);
        return "success";
    }

    @Override
    public String findAll() {
        System.out.println(student1Dao.findStudent1EntityList("student1", 20));
        System.out.println(student2Dao.findStudent2EntityList("student2", 20));;
        return "success";
    }

    @Override
    public Student findByCondition(String name, Integer age) {
        return null;
    }

    @Override
    public List<Student> findByListCondition(List<String> teacherNameList) {
        return null;
    }
}
