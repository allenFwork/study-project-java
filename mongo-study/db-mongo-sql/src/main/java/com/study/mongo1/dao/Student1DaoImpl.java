package com.study.mongo1.dao;

import com.study.mongo1.entity.Student1Entity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 具体的操作类实现：基础方法的实现都已经封装在了 BasicDaoImpl中了
 * 这个实现类最重要的就是传的泛型进行了确定：Student1Entity.class
 */
@Component
public class Student1DaoImpl extends Mongo1BasicDaoImpl<Student1Entity> implements Student1Dao<Student1Entity> {

    @Override
    public Student1Entity findStudent1Entity(String name, Integer age) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("age").is(age));
        Student1Entity entity = mongoTemplate.findOne(query, Student1Entity.class);
        return entity;
    }

    @Override
    public List<Student1Entity> findStudent1EntityList(String name, Integer age) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("age").is(age));
        List<Student1Entity> list = mongoTemplate.find(query, Student1Entity.class);
        return list;
    }

}
