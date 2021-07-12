package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *      @SpringBootApplication
 * 等价于：
 *      @SpringBootConfiguration
 *      @EnableAutoConfiguration
 *      @ComponentScan
 */
@SpringBootApplication
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringBootDemoApplication.class);
        springApplication.run(args);
    }

}
