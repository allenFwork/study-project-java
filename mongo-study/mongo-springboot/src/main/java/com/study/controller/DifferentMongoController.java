package com.study.controller;

import com.study.service.DifferentMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DifferentMongoController {

    @Autowired
    private DifferentMongoService differentMongoService;

    @ResponseBody
    @RequestMapping("/mongo/different/insert")
    public String findDifferentAll() {
        return differentMongoService.insertOne();
    }


}
