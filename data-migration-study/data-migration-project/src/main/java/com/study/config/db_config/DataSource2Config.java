package com.study.config.db_config;

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
@MapperScan(basePackages = DataSource2Config.PACKAGE, sqlSessionFactoryRef = "secondSqlSessionFactory")
public class DataSource2Config {

    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.study.mapper.lec2.interfaces";
    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Bean(name = "datasource2")
    @ConfigurationProperties(prefix = "spring.datasource.datasource2")
    public DataSource clusterDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "secondTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager() {
        return new DataSourceTransactionManager(clusterDataSource());
    }

    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("datasource2") DataSource clusterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clusterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DataSource2Config.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
