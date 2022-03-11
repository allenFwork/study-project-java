package com.study.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = DataSource1Config.PACKAGE, sqlSessionFactoryRef = "firstSqlSessionFactory")
public class DataSource1Config {

    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.study.mapper.lec.interfaces";
    // 此处的 MAPPER_LOCATION 是所有mapper.xml文件的存放路径, 两个数据源都有所有mapper.xml映射文件配置
    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Bean(name = "datasource1")
    @ConfigurationProperties(prefix = "spring.datasource.datasource1")
    public DataSource clusterDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "firstTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager() {
        return new DataSourceTransactionManager(clusterDataSource());
    }

    @Primary // 通过该注解能够通过Mybatis需要SqlSessionFactoryBean时,有两个导致的异常问题
    @Bean(name = "firstSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("datasource1") DataSource clusterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clusterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DataSource1Config.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
