package com.study.mybatis_plus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.study.mybatis_plus.mapper")
@SpringBootApplication
public class MyBatisPlusDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlusDemoApplication.class);
    }
}
