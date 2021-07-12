package com.study.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 多数据源配置
 */
@Configuration
public class MultipleDataSourceConfiguration {

    @Bean
    @Primary
    public DataSource masterDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        DataSource dataSource = dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver")
                                                 .url("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false")
                                                 .username("root")
                                                 .password("123456").build();
        return dataSource;
    }

    @Bean
    public DataSource slaveDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        DataSource dataSource = dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/test2?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false")
                .username("root")
                .password("123456").build();
        return dataSource;
    }

}
