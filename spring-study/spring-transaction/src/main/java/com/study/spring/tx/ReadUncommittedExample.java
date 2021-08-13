package com.study.spring.tx;

import java.sql.*;

/**
 * Connection.TRANSACTION_READ_UNCOMMITTED
 * 允许读取未提交事务中的数据
 */
public class ReadUncommittedExample {

    static {
        try {
            openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/test2?serverTimezone=GMT%2B8",
                                        "root",
                                        "123456");
        return connection;
    }

    // 该方法没有提交事务，所以最终该插入操作不会修改数据库
    public static void insert(String accountName, String name, int money) {
        try {
            Connection connection = openConnection();
            // 设置关闭自动自动提交事务
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into account (accountName, user, money) values (?,?,?)");
            preparedStatement.setString(1, accountName);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, money);
            preparedStatement.executeUpdate();
            System.out.println("执行插入");
            Thread.sleep(30000);
            // 提交
//            connection.commit();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void select(String name, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM account where user = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                for (int i = 1; i <= 4 ; i++) {
                    System.out.print(resultSet.getString(i) + ", ");
                }
                System.out.println();
            }
            System.out.println("执行查询");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Thread run(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }

    public static void main(String[] args) {
        // 启动插入数据库线程
        Thread t1 = run(new Runnable() {
            @Override
            public void run() {
                insert("1111", "superman", 1000);
            }
        });
        // 启动查询数据库线程
        Thread t2 = run(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    Connection connection = openConnection();
                    // 将参数升级成 Connection.TRANSACTION_READ_COMMITTED 即可解决脏读的问题
//                    connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                    connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    select("superman", connection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
