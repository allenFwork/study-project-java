package com.study.entity;

import org.springframework.beans.factory.FactoryBean;

import java.sql.Driver;
import java.sql.DriverManager;

/**
 * 自定义创建对象 交由 spring 管理
 * 通过 org.springframework.beans.factory.FactoryBean 来实现管理第三方框架中的对象 非常常见，
 * 例如 SqlSessionFactoryBean
 */
public class DriverFactoryBean implements FactoryBean {

    private String jdbcUrl;

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public Object getObject() throws Exception {
        return DriverManager.getDriver(jdbcUrl);
    }

    public Class<?> getObjectType() {
        return Driver.class;
    }

    // 该对象是否是单例的
    public boolean isSingleton() {
        return true;
    }
}
