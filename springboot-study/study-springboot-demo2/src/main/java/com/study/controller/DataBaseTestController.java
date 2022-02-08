package com.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class DataBaseTestController {

    @Autowired
    public DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/testDbType")
    public void testDataBaseType() {
        // 自动装配数据源的类型：
        // springboot默认的数据源 class com.zaxxer.hikari.HikariDataSource
        System.out.println("自动装配数据源的类型：" + dataSource.getClass());
    }

    @RequestMapping("/testJdbcTemplate")
    public void testJdbcTemplate() {
        System.out.println("测试JdbcTemplate的配置是否成功");
        List<Map<String, Object>> userList = jdbcTemplate.queryForList("select * from user");
        System.out.println("查询结果||用户数量：" + userList.size());
    }
}
