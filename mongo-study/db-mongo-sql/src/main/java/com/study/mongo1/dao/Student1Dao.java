package com.study.mongo1.dao;

import com.study.mongo1.entity.Student1Entity;

import java.util.List;

public interface Student1Dao<T extends Student1Entity> extends Mongo1BasicDao<T> {

    Student1Entity findStudent1Entity(String name, Integer age);

    List<Student1Entity> findStudent1EntityList(String name, Integer age);

}
