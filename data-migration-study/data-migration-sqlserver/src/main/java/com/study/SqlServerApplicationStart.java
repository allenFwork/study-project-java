package com.study;

import com.study.util.DataBaseConnectPool;
import com.study.util.DataBaseConnectPool2;
import com.study.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

public class SqlServerApplicationStart {

    static DataBaseConnectPool dataBaseConnectPool1 = new DataBaseConnectPool();
    static DataBaseConnectPool2 dataBaseConnectPool2 = new DataBaseConnectPool2();

    public static void main(String[] args) {

        // 数据库中总记录数
        long rows = 1740916L;
//        long rows = 27854644L;  6985280

        // 核心线程数
        int kThreads = Runtime.getRuntime().availableProcessors() * 50;
        // 任务数(这里需要求一下平均每个任务需要执行的任务id大小是多少，实际测试中，20-30w 快则 2秒，慢则3-5秒，这个阈值是比较理想的 也就是 总记录数/任意数 等到想要的平均任务数)
//        Long pageSize = (rows / 8000) + 1;
        Long pageSize = rows / (kThreads - 1);

        // 线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(kThreads);

        // 数据起始位
        Long pageNo = 0L;

        System.out.println("开始时间：" + DateUtil.toDateString(new Date(), DateUtil.DATE_PATTERN2));
        // 存放线程执行结果
        List<Future> list = new Vector<>();
        // 执行多少次任务 = 结束位置不小于总记录数
        for (Long i = 0l; i < rows; i = pageNo) {
            Connection connection1 = dataBaseConnectPool1.getConnection();
            Connection connection2 = dataBaseConnectPool2.getConnection();
            final Future submit = threadPool.submit(new DataThread(pageNo, pageSize, connection1, connection2));
            // 结束id=开始id+任务数
            pageNo = pageNo + pageSize;
            list.add(submit);
        }
        // 打印结果
        list.forEach(dx -> {
            try {
                System.out.println(dx.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        System.out.println("结束时间：" + DateUtil.toDateString(new Date(), DateUtil.DATE_PATTERN2));
    }

    // 线程操作资源类
    static class DataThread implements Callable {

        private Connection connection1;
        private Connection connection2;
        private Long pageNo, pageSize;

        public DataThread(Long pageNo, Long pageSize, Connection connection1, Connection connection2) {
            this.connection1 = connection1;
            this.connection2 = connection2;
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            System.out.println("pageNo:" + pageNo + ",pageSize:" + pageSize);
        }

        @Override
        public Object call() throws Exception {
            System.out.println("[起始页码: " + pageNo + ", 结束页码: " + (pageNo + pageSize) + "] 开始执行 ... ");
            /**
             * JDBC的事务支持：
             *
             *  JDBC对事务的支持体现在三个方面：
             *  1. 自动提交模式(Auto-commit mode)：Connection提供了一个auto-commit的属性来指定事务何时结束。
             * 　 (1) 当 auto-commit 为 true 时，当每个独立SQL操作的执行完毕，事务立即自动提交，也就是说每个SQL操作都是一个事务。
             * 　　一个独立SQL操作什么时候算执行完毕，JDBC规范是这样规定的：
             * 　　   对数据操作语言(DML，如insert,update,delete)和数据定义语言(如create,drop)，语句一执行完就视为执行完毕。
             *        对select语句，当与它关联的ResultSet对象关闭时，视为执行完毕。
             * 　　   对存储过程或其他返回多个结果的语句，当与它关联的所有ResultSet对象全部关闭，所有update count(update,delete等语句操作影响的行数)和output parameter(存储过程的输出参数)都已经获取之后，视为执行完毕。
             * 　 (2)当auto-commit为false时，每个事务都必须显示调用commit方法进行提交，或者显示调用rollback方法进行回滚。auto-commit默认为true。
             */
            connection1.setAutoCommit(true);
            connection2.setAutoCommit(false);
            try {
                PreparedStatement preparedStatement = connection1.prepareStatement("SELECT * FROM Tactical_Offline..sc_ship_part_cml_copy33 ORDER BY product,sbb OFFSET ? rows fetch next ? rows only");
                preparedStatement.setLong(1, pageNo);
                preparedStatement.setLong(2, pageSize);
                System.out.println("executeQuery执行前");
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("executeQuery执行后");
                PreparedStatement preparedStatement2 =
                        connection2.prepareStatement("INSERT INTO HAWK_TACTICAL..sc_ship_part_cml " +
                                "(bu,product_group,brand,plant_code,geo,subgeo,site_id,family,product,product_qty,sbb,sbb_qty,part,part_qty,ship_date,sys_created_by,update_timestamp,itemgroup,sci_site_id,product_line,ludp_update_timestamp,segment) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                while (resultSet.next()) {
                    preparedStatement2.setString(1, resultSet.getString(1));
                    preparedStatement2.setString(2, resultSet.getString(2));
                    preparedStatement2.setString(3, resultSet.getString(3));
                    preparedStatement2.setString(4, resultSet.getString(4));
                    preparedStatement2.setString(5, resultSet.getString(5));
                    preparedStatement2.setString(6, resultSet.getString(6));
                    preparedStatement2.setString(7, resultSet.getString(7));
                    preparedStatement2.setString(8, resultSet.getString(8));
                    preparedStatement2.setString(9, resultSet.getString(9));
                    preparedStatement2.setFloat(10, resultSet.getFloat(10));
                    preparedStatement2.setString(11, resultSet.getString(11));
                    preparedStatement2.setFloat(12, resultSet.getFloat(12));
                    preparedStatement2.setString(13, resultSet.getString(13));
                    preparedStatement2.setFloat(14, resultSet.getFloat(14));
                    preparedStatement2.setString(15, resultSet.getString(15));
                    preparedStatement2.setString(16, resultSet.getString(16));
                    preparedStatement2.setString(17, resultSet.getString(17));
                    preparedStatement2.setString(18, resultSet.getString(18));
                    preparedStatement2.setString(19, resultSet.getString(19));
                    preparedStatement2.setString(20, resultSet.getString(20));
                    preparedStatement2.setString(21, resultSet.getString(21));
                    preparedStatement2.setString(22, resultSet.getString(22));
                    preparedStatement2.executeUpdate();
                    connection2.commit();
                }
                System.out.println("executeUpdate执行后");
                // 关闭了查询的事务(自动提交)
                resultSet.close();
                preparedStatement.close();
                preparedStatement2.close();
            } catch (Exception e) {
                e.printStackTrace();
                return "[起始页码: " + pageNo + ", 结束页码: " + (pageNo + pageSize) + "] 执行失败! ";
            } finally {
                // 连接放回连接池中重复利用
//                dataBaseConnectPool1.releaseConnection(connection1);
//                dataBaseConnectPool2.releaseConnection(connection2);
                // 直接关闭连接
                connection1.close();
                connection2.close();
            }
            return "[起始页码: " + pageNo + ", 结束页码: " + (pageNo + pageSize) + "] 执行成功! ";
        }
    }

}
