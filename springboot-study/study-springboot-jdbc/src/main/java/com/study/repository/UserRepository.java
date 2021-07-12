package com.study.repository;

import com.study.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

/**
 * User的仓储（SQL、或NoSQL、或内存型）
 */
@Repository
public class UserRepository {

    private final DataSource dataSource;

    private final DataSource masterDataSource;

    private final DataSource slaveDataSource;

    private final JdbcTemplate jdbcTemplate;

    /**
     * 使用spring-boot-starter-jdbc或spring-boot-starter-data-jpa依赖时，
     * 框架会自动默认分别注入 DataSourceTransactionManager 或 JpaTransactionManager
     *
     * org.springframework.transaction.jta.JtaTransactionManager实现了PlatformTransactionManager接口
     */
    private final PlatformTransactionManager platformTransactionManager;


    /**
     * 通过构造器注入
     */
    @Autowired
    public UserRepository(DataSource dataSource,
                          @Qualifier("masterDataSource") DataSource masterDataSource,
                          @Qualifier("slaveDataSource") DataSource slaveDataSource,
                          JdbcTemplate jdbcTemplate,
                          PlatformTransactionManager platformTransactionManager) {
        this.dataSource = dataSource;
        this.masterDataSource = masterDataSource;
        this.slaveDataSource = slaveDataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.platformTransactionManager = platformTransactionManager;
    }

    // 注解驱动事务
    @Transactional
    public boolean saveUser(User user) {
        System.out.printf("[Thread : %s] save user : %s\n", Thread.currentThread().getName(), user);

        boolean success = false;

        // jdbcTemplate封装了很多方法，下面这个方法能够执行方法的回调
        success = jdbcTemplate.execute("insert into user (name) values (?)", new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setString(1, user.getName());
                return preparedStatement.executeUpdate() > 1 ;
            }
        });

//        throw new NullPointerException("抛异常"); 测试异常后，数据库不进行任何数据上的修改

        return success;
    }

    // api管理事务
//    public boolean saveUserByApi(User user) {
//        return false;
//    }

    public boolean jdbcSaveUser(User user) {
        boolean success = false;
        Connection connection = null;
        try {
            // 默认事务自动提交
            connection = dataSource.getConnection();
            // 将事务提交自动取消掉，改为手动提交
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into user (name) values (?)");
            preparedStatement.setString(1, user.getName());
            success = preparedStatement.executeUpdate() > 0; // 设为手动提交后，执行完此行代码，数据库没有增加数据
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    // 手动提交事务,执行完此代码，数据库才进行了数据操作
                    connection.commit();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return success;
    }

    public Collection<User> findAll() {
        return Collections.emptyList();
    }

}
