package com.study.spring.tx;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * spring 编程实例
 */
public class SpringTransactionExample {

    private static String url = "jdbc:mysql://localhost:3306/test2?serverTimezone=GMT%2B8";
    private static String user = "root";
    private static String password = "123456";

    public static Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/test2?serverTimezone=GMT%2B8",
                        "root",
                        "123456");
        return connection;
    }

    public static void main(String[] args) {

        final DataSource dataSource = new DriverManagerDataSource(url, user, password);

        // TransactionTemplate extends DefaultTransactionDefinition
        // DefaultTransactionDefinition implements TransactionDefinition
        final TransactionTemplate transactionTemplate = new TransactionTemplate();
        // 设置事务管理器
        transactionTemplate.setTransactionManager(new DataSourceTransactionManager(dataSource));

        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                /*
                 *  1.通过 DataSourceUtils.getConnection(dataSource)
                 *    获取的连接对象 与 TransactionStatus对象中的连接对象是同一个
                 *
                 *  2.通过 dataSource.getConnection() 获取的连接对象 与 TransactionStatus对象中的链接对象不是同一个，
                 *    所以下面的事务失效，每执行完一个insert 或 update 操作，事务自动提交了，出现异常事务直接回滚
                 *    想要使用事务，需要先关闭事务自动提交，设置对应的提交 和 回滚（就是原生操作jdbc事务）
                 */
                Connection connection = DataSourceUtils.getConnection(dataSource);
//                Connection connection = dataSource.getConnection();
                Object savePoint = null;
                try {

                    {
                        // 执行插入语句
                        PreparedStatement preparedStatement =
                                connection.prepareStatement("insert into account (accountName, user, money) values (?, ?, ?)");
                        preparedStatement.setString(1, "111");
                        preparedStatement.setString(2, "batman");
                        preparedStatement.setInt(3, 2000);
                        preparedStatement.executeUpdate();
                    }

                    // 设置保存点（执行完第一个插入语句后的保存点）
                    savePoint = transactionStatus.createSavepoint();

                    {
                        // 执行插入语句
                        PreparedStatement preparedStatement =
                                connection.prepareStatement("insert into account (accountName, user, money) values (?,?,?)");
                        preparedStatement.setString(1, "222");
                        preparedStatement.setString(2, "batman");
                        preparedStatement.setInt(3, 2000);
                        preparedStatement.executeUpdate();
                    }

                    {
                        // 执行更新语句，执行过程中出现异常
                        PreparedStatement preparedStatement =
                                connection.prepareStatement("update account set money = money + 1 where user = ?");
                        preparedStatement.setString(1, "xxx");
                        preparedStatement.executeUpdate();

                        // 分母为0，报错
                        int i = 1 / 0;

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println("更新失败");
                    // 回滚
                    if (savePoint != null) {
                        transactionStatus.rollbackToSavepoint(savePoint);
                    } else {
                        transactionStatus.setRollbackOnly();
                    }
                }
                return null;
            }
        });
    }

}
