package com.study.mongo2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mongo2BasicDaoImpl<T> implements Mongo2BasicDao<T> {

    @Autowired
    @Qualifier("mongoTemplate2")
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
