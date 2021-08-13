package com.study.spring.tx;

import java.sql.*;

public class ReadCommittedExample {

    static {
        try {
            openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Object lock = new Object();

    public static Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/test2?serverTimezone=GMT%2B8",
                        "root",
                        "123456");
        return connection;
    }

    // 自动进行提交，数据库数据会被修改
    public static void insert(String accountName, String name, int money) {
        try {
            Connection connection = openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into account (accountName, user, money) values (?,?,?)");
            preparedStatement.setString(1, accountName);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, money);
            preparedStatement.executeUpdate();
            System.out.println("执行插入成功");
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void select(String name, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM account where user = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("执行查询");
            while (resultSet.next()) {
                for (int i = 1; i <= 4 ; i++) {
                    System.out.print(resultSet.getString(i) + ", ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Thread run(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }

    // 测试先查询， 再插入，再查询 逻辑
    public static void main(String[] args) {
        // 启动插入数据库线程
        Thread t1 = run(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lock) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                insert("111", "superman", 1000);
            }
        });

        // 启动查询数据库线程
        Thread t2 = run(new Runnable() {
            @Override
            public void run() {
                try {

                    Connection connection = openConnection();
                    connection.setAutoCommit(false);
                    // 将参数升级成 Connection.TRANSACTION_READ_COMMITTED 即可解决脏读问题
                    connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    select("superman", connection);

                    // 释放锁
                    synchronized (lock) {
                        lock.notify();
                    }

                    // 第二次读到（数据不一致）
                    Thread.sleep(500);
                    select("superman", connection);
                    connection.close();
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
