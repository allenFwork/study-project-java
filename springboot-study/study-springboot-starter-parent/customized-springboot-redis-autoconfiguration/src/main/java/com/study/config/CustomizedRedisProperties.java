package com.study.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "customized.spring.redis")
public class CustomizedRedisProperties {

    private String host = "localhost";
    private int port = 6379;
    private String password;
    private Integer maxTotal = 50;
    private Integer minIdel = 5;
    private Integer maxIdel = 20;
    private Duration timeout;
    // 从连接池中借出jedis都会经过测试
    private boolean testOnBorrow = true;
    // 返回jedis到池中Jedis实例会经过测试
    private boolean testOnReturn = false;

    public CustomizedRedisProperties() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMinIdel() {
        return minIdel;
    }

    public void setMinIdel(Integer minIdel) {
        this.minIdel = minIdel;
    }

    public Integer getMaxIdel() {
        return maxIdel;
    }

    public void setMaxIdel(Integer maxIdel) {
        this.maxIdel = maxIdel;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }
}
