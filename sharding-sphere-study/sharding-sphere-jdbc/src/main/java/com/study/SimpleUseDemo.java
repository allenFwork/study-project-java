package com.study;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通过 java 代码原生使用 sharding-sphere 实现分库分表 demo
 */
public class SimpleUseDemo {

    public static void main(String[] args) throws SQLException {

        /*====================================== 多数据源配置(开始) ======================================*/
        // 配置真是数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置第一个数据源 BasicDataSource是commons-dbcp jar包下的类
        BasicDataSource dataSource1 = new BasicDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3307/orders_0");
        dataSource1.setUsername("root");
        dataSource1.setPassword("");
        dataSourceMap.put("orders_0", dataSource1);

        // 配置第二个数据源
        BasicDataSource dataSource2 = new BasicDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://localhost:3307/orders_1");
        dataSource2.setUsername("root");
        dataSource2.setPassword("");
        dataSourceMap.put("orders_1", dataSource2);
        /*====================================== 多数据源配置(结束) ======================================*/

        /*====================================== 配置分片规则(开始) ======================================*/
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        // t_order表的分片规则
        shardingRuleConfiguration.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        // t_order_item表的分片规则
        shardingRuleConfiguration.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
        // 设置绑定表: t_order与t_order_item绑定在一起
        shardingRuleConfiguration.getBindingTableGroups().add("t_order, t_order_item");

        // dataNode为数据分片最小单元
        // 配置分库策略 : 采用数据库的分片是用user_id字段, user_id % 2 用来定位数据库(InlineShardingStrategyConfiguration 行表达式分片策略)
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "orders_${user_id % 2}"));
        // sharding-column 分片字段 : 对表分片是采用 order_id 字段
        shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new PreciseShardingAlgorithm<Long>() {
            /**
             * @param collection           collection是一个 LinkedHashSet 对象,包含两个值：“t_order_0” 和 “ t_order_1”
             * @param preciseShardingValue 分装了此时的 逻辑表名: t_order, 字段名: order_id, 字段对应的值: xxx
             */
            @Override
            public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
                for (String each : collection) {
                    // 只会产生偶数的订单
                    // preciseShardingValue.getValue() 获取逻辑表指定指定字段(order_id)的值
                    if (each.endsWith(preciseShardingValue.getValue() % 2 + "")) {
                        return each;
                    }
                }
                throw new UnsupportedOperationException();
            }
        }));
        /*====================================== 配置分片规则(结束) ======================================*/

        Properties properties = new Properties();
        // 通过 sql.show 来配置是否显示 sql
        properties.setProperty("sql.show", "true");
        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, new ConcurrentHashMap<>(), properties);

        // 删除表 - 创建表 - 插入数据 （经过分片处理的）
        SimpleUseDemo simpleUseDemo = new SimpleUseDemo();
        simpleUseDemo.drop(dataSource);
        simpleUseDemo.create(dataSource);
        simpleUseDemo.insertData(dataSource);
    }

    public static TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration orderTableRuleConfiguration = new TableRuleConfiguration();
        // 设置逻辑表
        orderTableRuleConfiguration.setLogicTable("t_order");
        // 设置物理表：实际存在的表 - 最小的数据节点 数据库名.表名
        orderTableRuleConfiguration.setActualDataNodes("orders_${0..1}.t_order_${[0, 1]}");
        // 设置自动生成的主键
        orderTableRuleConfiguration.setKeyGeneratorColumnName("order_id");
        return orderTableRuleConfiguration;
    }

    public static TableRuleConfiguration getOrderItemTableRuleConfiguration() {
        TableRuleConfiguration orderItemTableRuleConfiguration = new TableRuleConfiguration();
        // 设置逻辑表
        orderItemTableRuleConfiguration.setLogicTable("t_order_item");
        // 设置物理表：实际存在的表 - 最小的数据节点 数据库名.表名
        orderItemTableRuleConfiguration.setActualDataNodes("orders_${0..1}.t_order_item_${[0, 1]}");
        orderItemTableRuleConfiguration.setKeyGeneratorColumnName("order_id");
        return orderItemTableRuleConfiguration;
    }

    public void drop(DataSource dataSource) throws SQLException {
        execute(dataSource, "DROP TABLE IF EXISTS t_order");
        execute(dataSource, "DROP TABLE IF EXISTS t_order_item");
    }

    public void create(DataSource dataSource) throws SQLException {
        execute(dataSource, "CREATE TABLE IF NOT EXISTS t_order (order_id BIGINT AUTO_INCREMENT, user_id INT NOT NULL, status VARCHAR(50), PRIMARY KEY (order_id))");
        execute(dataSource, "CREATE TABLE IF NOT EXISTS t_order_item (order_item_id BIGINT AUTO_INCREMENT, order_id BIGINT, user_id INT NOT NULL, status VARCHAR(50), PRIMARY KEY (order_item_id))");
    }

    /**
     * 以用户为中心
     *
     * @param dataSource
     * @throws SQLException
     */
    public void insertData(DataSource dataSource) throws SQLException {
        for (int i = 1; i <= 10; i++) {
            long orderId = executeAndGetGeneratedKey(dataSource, "INSERT INTO t_order (user_id, status) VALUES (10, 'INIT')");
            execute(dataSource, String.format("INSERT INTO t_order_item (order_id, user_id) VALUES (%d, 10)", orderId));
            orderId = executeAndGetGeneratedKey(dataSource, "INSERT INTO t_order (user_id, status) VALUES (11, 'INIT')");
            execute(dataSource, String.format("INSERT INTO t_order_item (order_id, user_id) VALUES (%d, 11)", orderId));
        }
    }

    /**
     * 执行executeUpdate()时卡住不往下执行,解决方法：
     * 1.将事务提交(没成功)
     * 2.直接将流关闭(成功) 修改版本如下方法
     */
    public void executeDefault(final DataSource dataSource, final String sql) throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        connection.close();
    }

    public void execute(final DataSource dataSource, final String sql) throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
        } finally {
            connection.close();
        }
    }

    /**
     * 执行executeUpdate()时卡住不往下执行,解决方法：
     * 1.将事务提交(没成功)
     * 2.直接将流关闭(成功) 修改版本如下方法
     */
    public long executeAndGetGeneratedKeyDefault(final DataSource dataSource, final String sql) throws SQLException {
        long result = -1;
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            result = resultSet.getLong(1);
        }
        return result;
    }

    public long executeAndGetGeneratedKey(final DataSource dataSource, final String sql) throws SQLException {
        long result = -1;
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                result = resultSet.getLong(1);
            }
            statement.close();
            resultSet.close();
        } finally {
            connection.close();
        }
        return result;
    }

}
