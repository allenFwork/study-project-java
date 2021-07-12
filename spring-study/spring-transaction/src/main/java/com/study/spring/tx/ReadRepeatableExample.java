package com.study.spring.tx;

import java.sql.*;

public class ReadRepeatableExample {

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

    public static void update(String user) {
        try {
            Connection connection = openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update account set money = money + 1 where user = ?");
            preparedStatement.setString(1, user);
            preparedStatement.executeUpdate();
            connection.close();
            System.out.println("执行修改成功");
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

    // 测试先查询， 再修改，再查询 逻辑
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
                update("superman");
            }
        });

        // 启动查询数据库线程
        Thread t2 = run(new Runnable() {
            @Override
            public void run() {
                try {

                    Connection connection = openConnection();
                    connection.setAutoCommit(false);
                    // 将参数升级成 Connection.TRANSACTION_READ_COMMITTED 即可解决 重复读问题
                    connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
//                    connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                    // 第一读取
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
