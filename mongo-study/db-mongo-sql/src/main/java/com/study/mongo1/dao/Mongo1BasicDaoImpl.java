package com.study.mongo1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mongo1BasicDaoImpl<T> implements Mongo1BasicDao<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public void insertOne(T e) {
        mongoTemplate.insert(e);
    }

    @Override
    public void insertBatch(List<T> list) {
        mongoTemplate.insertAll(list);
    }


    @Override
    public long delete(T e) {
        return this.mongoTemplate.remove(e).getDeletedCount();
    }

}
