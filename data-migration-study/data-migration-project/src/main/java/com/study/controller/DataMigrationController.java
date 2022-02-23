package com.study.controller;

import com.study.service.LecService;
import com.study.time_task.SqlServerDataMigrationTimeTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataMigrationController {

    @Autowired
    private SqlServerDataMigrationTimeTask sqlServerDataMigrationTimeTask;

    @Autowired
    private LecService lecService;

    @RequestMapping(value = "/initData")
    @ResponseBody
    public String initDataClean() {
        sqlServerDataMigrationTimeTask.initDataClean();
        return "success";
    }

    @RequestMapping(value = "/count")
    @ResponseBody
    public String count() {
        lecService.selectAll();
        return "success";
    }

}
