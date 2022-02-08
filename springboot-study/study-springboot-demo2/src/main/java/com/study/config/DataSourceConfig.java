package com.study.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * jdbc以及数据源的自动装配原理解析学习：
 * 数据源的自动装配
 * org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
 */
public class DataSourceConfig {

//    @ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
//    @ConditionalOnMissingBean(DataSource.class)
//    @ConditionalOnProperty(name = "spring.datasource.type", havingValue = "org.apache.tomcat.jdbc.pool.DataSource")
//    static class TomcatDataSource {
//        @Bean
//        @ConfigurationProperties(prefix = "spring.datasource.tomcat")
//        public org.apache.tomcat.jdbc.pool.DataSource dataSource(DataSourceProperties dataSourceProperties) {
//            org.apache.tomcat.jdbc.pool.DataSouce dataSource = createDataSource();
//        }
//    }
//
//    @ConditionalOnClass(HikariDataSource.class)
//    @ConditionalOnMissingBean(DataSource.class)
//    @ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.zaxxer.hikariDataSource", matchIfMissing = true)
//    static class Hikari {
//        public HikariDataSource dataSource() {
//
//        }
//    }
//    static class Dbcp2 {
//
//    }

}
