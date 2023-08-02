package com.study.redis.apply;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * redis的相关具体应用： 点评项目服务
 */
@MapperScan("com.study.redis.apply.mapper")
@SpringBootApplication
public class RedisApplyApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplyApplication.class, args);
    }

}