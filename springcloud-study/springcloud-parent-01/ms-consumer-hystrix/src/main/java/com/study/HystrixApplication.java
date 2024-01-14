package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hystrix（豪猪）是由Netflix开源的一个延迟和容错库，
 * 用于隔离访问远程系统、服务或者第三方库，防止级联失败，从而提升系统的可用性与容错性
 */
@SpringBootApplication
public class HystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class);
    }

}
