package com.study.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.study.cache.mapper")
@SpringBootApplication
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.study.cache.CacheApplication.class, args);
    }

}
