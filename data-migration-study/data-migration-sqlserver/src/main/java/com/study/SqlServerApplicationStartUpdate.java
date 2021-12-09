package com.study;

import com.study.util.DataBaseConnectPool;
import com.study.util.DataBaseConnectPool2;
import com.study.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

public class SqlServerApplicationStartUpdate {

    static DataBaseConnectPool dataBaseConnectPool1 = new DataBaseConnectPool();
    static DataBaseConnectPool2 dataBaseConnectPool2 = new DataBaseConnectPool2();

    public static void main(String[] args) {

        // 数据库中总记录数
        long rows = 4713347L;

        // 核心线程数
        int kThreads = Runtime.getRuntime().availableProcessors() * 10; // 80
        System.out.println("线程数量: " + kThreads);
        // 任务数(这里需要求一下平均每个任务需要执行的任务id大小是多少，实际测试中，20-30w 快则 2秒，慢则3-5秒，这个阈值是比较理想的 也就是 总记录数/任意数 等到想要的平均任务数)
//        Long pageSize = (rows / 8000) + 1;
        Long pageSize = rows / kThreads;

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
            // 多线程同时获取时,可能出现连接池中是有的,但是拿到了null
            while (connection1 == null) {
                connection1 = dataBaseConnectPool1.getConnection();
            }
            while (connection2 == null) {
                connection2 = dataBaseConnectPool2.getConnection();
            }
            final Future submit = threadPool.submit(new DataThread(pageNo, pageSize, connection1, connection2));
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
        /**
         * shutdown()
         *    将线程池状态置为SHUTDOWN,并不会立即停止：
         *      停止接收外部submit的任务
         *      内部正在跑的任务和队列里等待的任务，会执行完
         *      等到第二步完成后，才真正停止
         * shutdownNow()
         *    将线程池状态置为STOP。企图立即停止，事实上不一定：
         *      跟shutdown()一样，先停止接收外部提交的任务
         *      忽略队列里等待的任务
         *      尝试将正在跑的任务interrupt中断
         *      返回未执行的任务列表
         */
        System.out.println("线程池是否shutdown：" + threadPool.isShutdown());
        System.out.println("线程池是否terminated：" + threadPool.isTerminated());
        // 优雅的关闭线程池
        threadPool.shutdown();
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
            try {
                System.out.println("[起始页码: " + pageNo + ", 结束页码: " + (pageNo + pageSize) + "] 开始执行 ... ");
                connection2.setAutoCommit(false);
                // 起始页码
                long pageNoByCount = pageNo;
                // 每页查询数量
                long pageSizeByCount = pageSize / 2000;
                // 剩余数量
                long leaveCount = pageSize % 2000;
//                String sql1 = "SELECT * FROM Tactical_Offline..sc_ship_part_cml_copy44 ORDER BY BU OFFSET ? rows fetch next ? rows only";
//                String sql2 = "INSERT INTO HAWK_TACTICAL..sc_ship_part_cml (bu,product_group,brand,plant_code,geo,subgeo,site_id,family,product,product_qty,sbb,sbb_qty,part,part_qty,ship_date,sys_created_by,update_timestamp,itemgroup,sci_site_id,product_line,ludp_update_timestamp,segment) " +
//                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

//                String sql1 = "SELECT * FROM Tactical_Offline..sc_dim_product_rt_cml_copy8 ORDER BY product OFFSET ? rows fetch next ? rows only";
//                String sql2 = "INSERT INTO HAWK_TACTICAL..sc_dim_product_rt_cml (product,maktx,extwg,brand,product_group,business_unit,prodh,ph1_desc,ph2_desc,ph3_desc,ph4_desc,matkl,sys_creation_date,product_family,mvgr3,effstartdate,effenddate,ph5_desc,ph6_desc,ph1,ph2,ph3,ph4,ph5,ph6,mbg_family) " +
//                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                String sql1 = "SELECT material, bu, commodity, itemgroup, leadtime, liabilitywindow, material_category, sys_creation_date, sys_last_modified_date, sys_created_by, sys_last_modified_by, site_code, commodity_source FROM Tactical_Offline..sc_parts_attribute_fact_rt_cml ORDER BY id OFFSET ? rows fetch next ? rows only";
                String sql2 = "INSERT INTO HAWK_TACTICAL..sc_parts_attribute_fact_rt_cml (material, bu, commodity, itemgroup, leadtime, liabilitywindow, material_category, sys_creation_date, sys_last_modified_date, sys_created_by, sys_last_modified_by, site_code, commodity_source) " +
                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement1 = null;
                PreparedStatement preparedStatement2 = null;
                for (int i = 0; i < pageSizeByCount; i++) {
                    preparedStatement1 = connection1.prepareStatement(sql1);
                    preparedStatement1.setLong(1, pageNoByCount);
                    preparedStatement1.setLong(2, 2000);
                    ResultSet resultSet = preparedStatement1.executeQuery();
                    preparedStatement2 = connection2.prepareStatement(sql2);
                    while (resultSet.next()) {
//                        preparedStatement2.setString(1, resultSet.getString(1));
//                        preparedStatement2.setString(2, resultSet.getString(2));
//                        preparedStatement2.setString(3, resultSet.getString(3));
//                        preparedStatement2.setString(4, resultSet.getString(4));
//                        preparedStatement2.setString(5, resultSet.getString(5));
//                        preparedStatement2.setString(6, resultSet.getString(6));
//                        preparedStatement2.setString(7, resultSet.getString(7));
//                        preparedStatement2.setString(8, resultSet.getString(8));
//                        preparedStatement2.setString(9, resultSet.getString(9));
//                        preparedStatement2.setFloat(10, resultSet.getFloat(10));
//                        preparedStatement2.setString(11, resultSet.getString(11));
//                        preparedStatement2.setFloat(12, resultSet.getFloat(12));
//                        preparedStatement2.setString(13, resultSet.getString(13));
//                        preparedStatement2.setFloat(14, resultSet.getFloat(14));
//                        preparedStatement2.setString(15, resultSet.getString(15));
//                        preparedStatement2.setString(16, resultSet.getString(16));
//                        preparedStatement2.setString(17, resultSet.getString(17));
//                        preparedStatement2.setString(18, resultSet.getString(18));
//                        preparedStatement2.setString(19, resultSet.getString(19));
//                        preparedStatement2.setString(20, resultSet.getString(20));
//                        preparedStatement2.setString(21, resultSet.getString(21));
//                        preparedStatement2.setString(22, resultSet.getString(22));
//                        preparedStatement2.addBatch();

//                        preparedStatement2.setString(1, resultSet.getString(1));
//                        preparedStatement2.setString(2, resultSet.getString(2));
//                        preparedStatement2.setString(3, resultSet.getString(3));
//                        preparedStatement2.setString(4, resultSet.getString(4));
//                        preparedStatement2.setString(5, resultSet.getString(5));
//                        preparedStatement2.setString(6, resultSet.getString(6));
//                        preparedStatement2.setString(7, resultSet.getString(7));
//                        preparedStatement2.setString(8, resultSet.getString(8));
//                        preparedStatement2.setString(9, resultSet.getString(9));
//                        preparedStatement2.setString(10, resultSet.getString(10));
//                        preparedStatement2.setString(11, resultSet.getString(11));
//                        preparedStatement2.setString(12, resultSet.getString(12));
//                        preparedStatement2.setString(13, resultSet.getString(13));
//                        preparedStatement2.setString(14, resultSet.getString(14));
//                        preparedStatement2.setString(15, resultSet.getString(15));
//                        preparedStatement2.setString(16, resultSet.getString(16));
//                        preparedStatement2.setString(17, resultSet.getString(17));
//                        preparedStatement2.setString(18, resultSet.getString(18));
//                        preparedStatement2.setString(19, resultSet.getString(19));
//                        preparedStatement2.setString(20, resultSet.getString(20));
//                        preparedStatement2.setString(21, resultSet.getString(21));
//                        preparedStatement2.setString(22, resultSet.getString(22));
//                        preparedStatement2.setString(23, resultSet.getString(23));
//                        preparedStatement2.setString(24, resultSet.getString(24));
//                        preparedStatement2.setString(25, resultSet.getString(25));
//                        preparedStatement2.setString(26, resultSet.getString(26));
//                        preparedStatement2.addBatch();

                        preparedStatement2.setString(1, resultSet.getString(1));
                        preparedStatement2.setString(2, resultSet.getString(2));
                        preparedStatement2.setString(3, resultSet.getString(3));
                        preparedStatement2.setString(4, resultSet.getString(4));
                        preparedStatement2.setFloat(5, resultSet.getFloat(5));
                        preparedStatement2.setFloat(6, resultSet.getFloat(6));
                        preparedStatement2.setString(7, resultSet.getString(7));
                        preparedStatement2.setString(8, resultSet.getString(8));
                        preparedStatement2.setString(9, resultSet.getString(9));
                        preparedStatement2.setString(10, resultSet.getString(10));
                        preparedStatement2.setString(11, resultSet.getString(11));
                        preparedStatement2.setString(12, resultSet.getString(12));
                        preparedStatement2.setString(13, resultSet.getString(13));
                        preparedStatement2.addBatch();

                    }
                    preparedStatement2.executeBatch();
                    connection2.commit();
                    resultSet.close();
                    pageNoByCount = pageNoByCount + 2000;
                }
                if (leaveCount > 0) {
                    preparedStatement1 = connection1.prepareStatement(sql1);
                    preparedStatement1.setLong(1, pageNoByCount);
                    preparedStatement1.setLong(2, leaveCount);
                    ResultSet resultSet = preparedStatement1.executeQuery();
                    preparedStatement2 = connection2.prepareStatement(sql2);
                    while (resultSet.next()) {
//                        preparedStatement2.setString(1, resultSet.getString(1));
//                        preparedStatement2.setString(2, resultSet.getString(2));
//                        preparedStatement2.setString(3, resultSet.getString(3));
//                        preparedStatement2.setString(4, resultSet.getString(4));
//                        preparedStatement2.setString(5, resultSet.getString(5));
//                        preparedStatement2.setString(6, resultSet.getString(6));
//                        preparedStatement2.setString(7, resultSet.getString(7));
//                        preparedStatement2.setString(8, resultSet.getString(8));
//                        preparedStatement2.setString(9, resultSet.getString(9));
//                        preparedStatement2.setFloat(10, resultSet.getFloat(10));
//                        preparedStatement2.setString(11, resultSet.getString(11));
//                        preparedStatement2.setFloat(12, resultSet.getFloat(12));
//                        preparedStatement2.setString(13, resultSet.getString(13));
//                        preparedStatement2.setFloat(14, resultSet.getFloat(14));
//                        preparedStatement2.setString(15, resultSet.getString(15));
//                        preparedStatement2.setString(16, resultSet.getString(16));
//                        preparedStatement2.setString(17, resultSet.getString(17));
//                        preparedStatement2.setString(18, resultSet.getString(18));
//                        preparedStatement2.setString(19, resultSet.getString(19));
//                        preparedStatement2.setString(20, resultSet.getString(20));
//                        preparedStatement2.setString(21, resultSet.getString(21));
//                        preparedStatement2.setString(22, resultSet.getString(22));
//                        preparedStatement2.addBatch();

//                        preparedStatement2.setString(1, resultSet.getString(1));
//                        preparedStatement2.setString(2, resultSet.getString(2));
//                        preparedStatement2.setString(3, resultSet.getString(3));
//                        preparedStatement2.setString(4, resultSet.getString(4));
//                        preparedStatement2.setString(5, resultSet.getString(5));
//                        preparedStatement2.setString(6, resultSet.getString(6));
//                        preparedStatement2.setString(7, resultSet.getString(7));
//                        preparedStatement2.setString(8, resultSet.getString(8));
//                        preparedStatement2.setString(9, resultSet.getString(9));
//                        preparedStatement2.setString(10, resultSet.getString(10));
//                        preparedStatement2.setString(11, resultSet.getString(11));
//                        preparedStatement2.setString(12, resultSet.getString(12));
//                        preparedStatement2.setString(13, resultSet.getString(13));
//                        preparedStatement2.setString(14, resultSet.getString(14));
//                        preparedStatement2.setString(15, resultSet.getString(15));
//                        preparedStatement2.setString(16, resultSet.getString(16));
//                        preparedStatement2.setString(17, resultSet.getString(17));
//                        preparedStatement2.setString(18, resultSet.getString(18));
//                        preparedStatement2.setString(19, resultSet.getString(19));
//                        preparedStatement2.setString(20, resultSet.getString(20));
//                        preparedStatement2.setString(21, resultSet.getString(21));
//                        preparedStatement2.setString(22, resultSet.getString(22));
//                        preparedStatement2.setString(23, resultSet.getString(23));
//                        preparedStatement2.setString(24, resultSet.getString(24));
//                        preparedStatement2.setString(25, resultSet.getString(25));
//                        preparedStatement2.setString(26, resultSet.getString(26));
//                        preparedStatement2.addBatch();

                        preparedStatement2.setString(1, resultSet.getString(1));
                        preparedStatement2.setString(2, resultSet.getString(2));
                        preparedStatement2.setString(3, resultSet.getString(3));
                        preparedStatement2.setString(4, resultSet.getString(4));
                        preparedStatement2.setFloat(5, resultSet.getFloat(5));
                        preparedStatement2.setFloat(6, resultSet.getFloat(6));
                        preparedStatement2.setString(7, resultSet.getString(7));
                        preparedStatement2.setString(8, resultSet.getString(8));
                        preparedStatement2.setString(9, resultSet.getString(9));
                        preparedStatement2.setString(10, resultSet.getString(10));
                        preparedStatement2.setString(11, resultSet.getString(11));
                        preparedStatement2.setString(12, resultSet.getString(12));
                        preparedStatement2.setString(13, resultSet.getString(13));
                        preparedStatement2.addBatch();
                    }
                    preparedStatement2.executeBatch();
                    connection2.commit();
                    resultSet.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "[起始页码: " + pageNo + ", 结束页码: " + (pageNo + pageSize) + "] 执行失败! ";
            } finally {
                dataBaseConnectPool1.releaseConnection(connection1);
                dataBaseConnectPool2.releaseConnection(connection2);
            }
            return "[起始页码: " + pageNo + ", 结束页码: " + (pageNo + pageSize) + "] 执行成功! ";
        }
    }

}
