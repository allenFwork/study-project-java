package com.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Description 操作数据库,jdbc工作类
 * @Versrion 1.0
 * @Author sw
 */
public class JDBCUtil {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.jdbc.mysql.Driver");
            connection = DriverManager.getConnection("", "root", "123456");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
