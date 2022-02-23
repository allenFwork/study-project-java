package com.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"com.study.mapper.lec.interfaces", "com.study.mapper.lec2.interfaces"})
public class DataMigrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataMigrationApplication.class);
    }

}
