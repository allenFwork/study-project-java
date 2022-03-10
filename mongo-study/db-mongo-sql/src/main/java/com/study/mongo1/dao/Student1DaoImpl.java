package com.study.mongo1.dao;

import com.study.mongo1.entity.Student1Entity;
import org.springframework.stereotype.Component;

/**
 * 具体的操作类实现：基础方法的实现都已经封装在了 BasicDaoImpl中了
 * 这个实现类最重要的就是传的泛型进行了确定：Student1Entity.class
 */
@Component
public class Student1DaoImpl extends Mongo1BasicDaoImpl<Student1Entity> implements Student1Dao<Student1Entity> {

}
