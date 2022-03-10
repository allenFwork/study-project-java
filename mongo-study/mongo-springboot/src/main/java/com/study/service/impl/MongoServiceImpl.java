package com.study.service.impl;

import com.study.entity.Student;
import com.study.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MongoServiceImpl implements MongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String insertOne() {
        Student student = new Student();
        student.setName("superman");
        student.setAge(18);
        List scoreList = new ArrayList();
        scoreList.add(60);
        scoreList.add(70);
        scoreList.add(80);
        scoreList.add(90);
        scoreList.add(100);
        student.setScoreList(scoreList);
        Map<String, Date> map = new HashMap();
        map.put("Chinese", new Date());
        map.put("English", new Date());
        student.setSubjects(map);
        Student.Teacher teacher = new Student.Teacher("teacher1");
        student.setTeacher(teacher);
        /**
         * 1.插入对象到集合中时,如果数据库中没有该集合,mongo回自动创建该集合,然后插入该条数据
         */
        mongoTemplate.insert(student);

        Student student2 = new Student();
        student2.setName("batman");
        student2.setTeacher(new Student.Teacher("teacher2"));
        mongoTemplate.insert(student2);

        Student student3 = new Student();
        student2.setName("superGirl");
        student2.setTeacher(new Student.Teacher("teacher3"));
        mongoTemplate.insert(student3);
        return "success";
    }

    @Override
    public String findAll() {
        List<Student> studentsList = mongoTemplate.findAll(Student.class);
        System.out.println(studentsList);
        return "success";
    }

    /**
     * 查询根据某些属性进行查询
     * @param name
     * @param age
     * @return
     */
    @Override
    public Student findByCondition(String name, Integer age) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("age").is(age));
        /**
         * 查询集合中满足条件的数据：
         * 如果集合中有多条满足该条件的数据,查询不会报错,只会查询出第一条数据
         */
        Student entity = mongoTemplate.findOne(query, Student.class);
        System.out.println(entity);
        return entity;
    }

    /**
     * 根据某个属性查询是否在某个集合中
     * @param teacherNameList
     * @return
     */
    @Override
    public List<Student> findByListCondition(List<String> teacherNameList) {
        Query query = new Query();
        Criteria codeCriteria = Criteria.where("teacher."+"name").in(teacherNameList);
        query.addCriteria(codeCriteria);
        List<Student> entities = mongoTemplate.find(query, Student.class);
        System.out.println(entities);
        return entities;
    }

}
