package com.study.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.study.dao")
public class DataSourceConfig {

    // 注册数据源,并绑定配置文件中以 spring.datasource 前缀开头的相关属性
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource createDataSource() {
        DataSource dataSource = new DruidDataSource();
        return dataSource;
    }

}
