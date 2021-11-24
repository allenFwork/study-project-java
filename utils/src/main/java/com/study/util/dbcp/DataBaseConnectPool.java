package com.study.util.dbcp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数据库连接池的实现及原理
 * 对于一个简单的数据库应用，由于对于数据库的访问不是很频繁。这时可以简单地在需要访问数据库时，就新创建一个连接，
 * 用完后就关闭它，这样做也不会带来什么明显的性能上的开销。但是对于一个复杂的数据库应用，情况就完全不同了。
 * 频繁的建立、关闭连接，会极大的减低系统的性能，因为对于连接的使用成了系统性能的瓶颈。
 * <p>
 * 连接复用。通过建立一个数据库连接池以及一套连接使用管理策略，使得一个数据库连接可以得到高效、
 * 安全的复用，避免了数据库连接频繁建立、关闭的开销。
 * <p>
 * 对于共享资源，有一个很著名的设计模式：资源池。该模式正是为了解决资源频繁分配、释放所造成的问题的。
 * 把该模式应用到数据库连接管理领域，就是建立一个数据库连接池，提供一套高效的连接分配、使用策略，最终目标是实现连接的高效、安全的复用。
 * <p>
 * 数据库连接池的基本原理是在内部对象池中维护一定数量的数据库连接，并对外暴露数据库连接获取和返回方法。
 * 如：外部使用者可通过getConnection方法获取连接，使用完毕后再通过releaseConnection 方法将连接返回，
 * 注意此时连接并没有关闭，而是由连接池管理器回收，并为下一次使用做好准备。
 */
public class DataBaseConnectPool {

    /**
     * DBCP最常用的资源有三类:
     * Connection: 数据库连接
     * Statement:  会话声明
     * ResultSet:  结果集游标
     * 这是一种 “爷—父—子” 的关系，对Connection的管理，就是对数据库资源的管理。
     * 举个例子: 如果想确定某个数据库连接(Connection)是否超时，则需要确定其（所有的）子Statement是否超时，
     * 同样，需要确定所有相关的 ResultSet是否超时；在关闭Connection前，需要关闭所有相关的Statement和ResultSet。
     * 因此，连接池(Connection Pool)所起到的作用，不仅仅简单地管理Connection，还涉及到 Statement和ResultSet
     */

    // 使用容器 使用环境查询条件
    private LinkedList<Connection> connectPool = new LinkedList<>();

    // 配置文件中获取的配置信息
    private static final int INIT_CONNECTIONS = 10;
    private static final String DRIVER_CLASS = "";
    private static final String URL = "";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    static {
        // 注册驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 通过构造方法初始化连接
    public DataBaseConnectPool() {
        for (int i = 0; i < INIT_CONNECTIONS; i++) {
            try {
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                connectPool.add(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 获取连接
    public Connection getConnection() {
        Connection connection = null;
        // 加锁 - 获取锁
        lock.lock();
        try {
            while (connectPool.size() < 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!connectPool.isEmpty()) {
                connection = connectPool.removeFirst();
            }
            return connection;
        } finally {
            // 释放锁
            lock.unlock();
        }

    }

    // 释放连接
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            lock.lock();
           try {
               // 释放连接过程就是把连接放回连接池过程
               connectPool.addLast(connection);
               condition.signal();
           } finally {
               lock.unlock();
           }
        }
    }

}
