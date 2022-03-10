package com.study.mongo2.dao;

import com.study.mongo2.entity.Student2Entity;

import java.util.List;

public interface Student2Dao<T extends Student2Entity> extends Mongo2BasicDao<T> {

    Student2Entity findStudent2Entity(String name, Integer age);

    List<Student2Entity> findStudent2EntityList(String name, Integer age);

}
