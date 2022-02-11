package com.study.test;

import com.study.config.RedisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportAutoConfiguration(value = {RedisAutoConfiguration.class})
public class CustomizedTestRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomizedTestRedisApplication.class);
    }

}
