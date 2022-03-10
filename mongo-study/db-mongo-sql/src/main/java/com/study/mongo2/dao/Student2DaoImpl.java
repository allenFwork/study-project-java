package com.study.mongo2.dao;

import com.study.mongo2.entity.Student2Entity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 具体的操作类实现：基础方法的实现都已经封装在了 BasicDaoImpl中了
 * 这个实现类最重要的就是传的泛型进行了确定：Student2Entity.class
 */
@Component
public class Student2DaoImpl extends Mongo2BasicDaoImpl<Student2Entity> implements Student2Dao<Student2Entity> {

    @Override
    public Student2Entity findStudent2Entity(String name, Integer age) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("age").is(age));
        Student2Entity entity = mongoTemplate.findOne(query, Student2Entity.class);
        return entity;
    }

    @Override
    public List<Student2Entity> findStudent2EntityList(String name, Integer age) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("age").is(age));
        List<Student2Entity> list = mongoTemplate.find(query, Student2Entity.class);
        return list;
    }
}
