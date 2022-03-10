package com.study.controller;

import com.study.entity.Student;
import com.study.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MongoController {

    @Autowired
    private MongoService mongoService;

    @ResponseBody
    @RequestMapping("/mongo/insert")
    public String testInsert() {
        return mongoService.insertOne();
    }

    @ResponseBody
    @RequestMapping("/mongo/findAll")
    public String findAll() {
        return mongoService.findAll();
    }

    @ResponseBody
    @RequestMapping("/mongo/findByCondition")
    public Student findByCondition(String name, Integer age) {
        return mongoService.findByCondition(name, age);
    }

    @ResponseBody
    @RequestMapping("/mongo/findByConditions")
    public List<Student> findByTeacherNames() {
        List<String> stringList = new ArrayList();
        stringList.add("teacher1");
        stringList.add("teacher2");
        return mongoService.findByListCondition(stringList);
    }

}
