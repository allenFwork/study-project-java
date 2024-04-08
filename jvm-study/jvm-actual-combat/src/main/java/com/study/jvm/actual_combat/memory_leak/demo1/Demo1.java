package com.study.jvm.actual_combat.memory_leak.demo1;

import java.sql.*;

/**
 * 内存泄漏原因：资源没有正常关闭
 * -Xmx50m -Xms50m
 */
public class Demo1 {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:///bank1";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "123456";

    public static void leak() {
        // Connection conn = null;
        Statement stmt = null;
        /*
         * 1.如果下面的代码创建了连接对象，虽然没有手动释放掉，但是因为leak()方法被调用结束，依然会不在GC Root引用链上，从而被回收掉
         * 2.JDK7以后，使用try-with-resources语法可以自动关闭资源
         */
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            // executes a valid query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, account_name FROM account_info";
            ResultSet rs = stmt.executeQuery(sql);

            // STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");
                String name = rs.getString("account_name");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Name: " + name + "\n");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            leak();
        }
    }
}
