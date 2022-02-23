package com.study.util.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public abstract class AbsDefaultDataSourceProcess<T, V>  extends AbstractDataProcess<T, V> {

    @Value("${mysql.DB.driver}")
    private String driver;

    @Override
    protected String getDriver() {
        log.info("driver = {} ", driver);
        return driver;
    }

    @Override
    protected Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://10.110.147.32:3306/dps?useUnicode=true&characterEncoding=UTF8&useSSL=false&useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull";
        return DriverManager.getConnection(url, "root", "mL32@mL!");
    }

}
