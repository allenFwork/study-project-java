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
        long rows = 4004272L;

        // 核心线程数
        int kThreads = Runtime.getRuntime().availableProcessors() * 2; // 80
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
            final Future submit = threadPool.submit(new DataThread(pageNo, pageSize, connection2, connection1));
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

//                String sql1 = "SELECT material, bu, commodity, itemgroup, leadtime, liabilitywindow, material_category, sys_creation_date, sys_last_modified_date, sys_created_by, sys_last_modified_by, site_code, commodity_source FROM Tactical_Offline..sc_parts_attribute_fact_rt_cml ORDER BY id OFFSET ? rows fetch next ? rows only";
//                String sql2 = "INSERT INTO HAWK_TACTICAL..sc_parts_attribute_fact_rt_cml (material, bu, commodity, itemgroup, leadtime, liabilitywindow, material_category, sys_creation_date, sys_last_modified_date, sys_created_by, sys_last_modified_by, site_code, commodity_source) " +
//                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                String sql1 = "SELECT 商品编码,商品名称,商品属性,计量单位,保管数,可卖数,三级账,预售数,延付数,暂存数,结算价,订单在途,\n" +
                                "订单金额,转仓在途,转仓在途金额,借出在途,借出在途金额,集团在途,集团在途金额,供应商编码,供应商,部门编码,采购部门,品牌,商品分类,一级分类名称,二级分类名称,三级分类名称,\n" +
                                "四级分类名称,平均单价,平均库存金额,三级账库存金额,可卖数库存金额,安全库存,上限库存,仓库编码,仓库名称,最早入库日期,门店库龄,最后入库日期,商品库龄,库存预警,\n" +
                                "零售单价,会员单价,最新进价,销售控制,仓库类型,保管结算金额,近1月销量,近3月销量,CPU型号,CPU品牌,机械硬盘,固态硬盘,显卡型号,AT,机械厚度,屏幕尺寸,运行内存,date FROM LENOVO_SKUJCSJ_SPKC_BACKUP_copy ORDER BY date,商品编码 OFFSET ? rows fetch next ? rows only";
                String sql2 = "INSERT INTO LENOVO_SKUJCSJ_SPKC_BACKUP_copy (商品编码,商品名称,商品属性,计量单位,保管数,可卖数,三级账,预售数,延付数,暂存数,结算价,订单在途,\n" +
                                            "订单金额,转仓在途,转仓在途金额,借出在途,借出在途金额,集团在途,集团在途金额,供应商编码,供应商,部门编码,采购部门,品牌,商品分类,一级分类名称,二级分类名称,三级分类名称,\n" +
                                            "四级分类名称,平均单价,平均库存金额,三级账库存金额,可卖数库存金额,安全库存,上限库存,仓库编码,仓库名称,最早入库日期,门店库龄,最后入库日期,商品库龄,库存预警,\n" +
                                            "零售单价,会员单价,最新进价,销售控制,仓库类型,保管结算金额,近1月销量,近3月销量,CPU型号,CPU品牌,机械硬盘,固态硬盘,显卡型号,AT,机械厚度,屏幕尺寸,运行内存,date) " +
                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // 60

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
                        preparedStatement2.setFloat(7, resultSet.getFloat(7));
                        preparedStatement2.setFloat(8, resultSet.getFloat(8));
                        preparedStatement2.setFloat(9, resultSet.getFloat(9));
                        preparedStatement2.setFloat(10, resultSet.getFloat(10));
                        preparedStatement2.setFloat(11, resultSet.getFloat(11));
                        preparedStatement2.setFloat(12, resultSet.getFloat(12));

                        preparedStatement2.setInt(13, resultSet.getInt(13));

                        preparedStatement2.setFloat(14, resultSet.getFloat(14));

                        preparedStatement2.setFloat(15, resultSet.getFloat(15));
                        preparedStatement2.setFloat(16, resultSet.getFloat(16));
                        preparedStatement2.setFloat(17, resultSet.getFloat(17));
                        preparedStatement2.setFloat(18, resultSet.getFloat(18));
                        preparedStatement2.setFloat(19, resultSet.getFloat(19));

                        preparedStatement2.setString(20, resultSet.getString(20));
                        preparedStatement2.setString(21, resultSet.getString(21));
                        preparedStatement2.setString(22, resultSet.getString(22));
                        preparedStatement2.setString(23, resultSet.getString(23));
                        preparedStatement2.setString(24, resultSet.getString(24));
                        preparedStatement2.setString(25, resultSet.getString(25));
                        preparedStatement2.setString(26, resultSet.getString(26));
                        preparedStatement2.setString(27, resultSet.getString(27));
                        preparedStatement2.setString(28, resultSet.getString(28));
                        preparedStatement2.setString(29, resultSet.getString(29));

                        preparedStatement2.setFloat(30, resultSet.getFloat(30));
                        preparedStatement2.setFloat(31, resultSet.getFloat(31));
                        preparedStatement2.setFloat(32, resultSet.getFloat(32));
                        preparedStatement2.setFloat(33, resultSet.getFloat(33));
                        preparedStatement2.setFloat(34, resultSet.getFloat(34));
                        preparedStatement2.setFloat(35, resultSet.getFloat(35));

                        preparedStatement2.setString(36, resultSet.getString(36));
                        preparedStatement2.setString(37, resultSet.getString(37));
                        preparedStatement2.setString(38, resultSet.getString(38));

                        preparedStatement2.setInt(39, resultSet.getInt(39));
                        preparedStatement2.setString(40, resultSet.getString(40));
                        preparedStatement2.setInt(41, resultSet.getInt(41));
                        preparedStatement2.setString(42, resultSet.getString(42));
                        preparedStatement2.setInt(43, resultSet.getInt(43));
                        preparedStatement2.setFloat(44, resultSet.getFloat(44));
                        preparedStatement2.setFloat(45, resultSet.getFloat(45));
                        preparedStatement2.setString(46, resultSet.getString(46));
                        preparedStatement2.setString(47, resultSet.getString(47));
                        preparedStatement2.setFloat(48, resultSet.getFloat(48));
                        preparedStatement2.setInt(49, resultSet.getInt(49));
                        preparedStatement2.setInt(50, resultSet.getInt(50));
                        preparedStatement2.setString(51, resultSet.getString(51));
                        preparedStatement2.setString(52, resultSet.getString(52));
                        preparedStatement2.setString(53, resultSet.getString(53));
                        preparedStatement2.setString(54, resultSet.getString(54));
                        preparedStatement2.setString(55, resultSet.getString(55));
                        preparedStatement2.setString(56, resultSet.getString(56));
                        preparedStatement2.setString(57, resultSet.getString(57));
                        preparedStatement2.setString(58, resultSet.getString(58));
                        preparedStatement2.setString(59, resultSet.getString(59));
                        preparedStatement2.setString(60, resultSet.getString(60));
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

//                        preparedStatement2.setString(1, resultSet.getString(1));
//                        preparedStatement2.setString(2, resultSet.getString(2));
//                        preparedStatement2.setString(3, resultSet.getString(3));
//                        preparedStatement2.setString(4, resultSet.getString(4));
//                        preparedStatement2.setFloat(5, resultSet.getFloat(5));
//                        preparedStatement2.setFloat(6, resultSet.getFloat(6));
//                        preparedStatement2.setString(7, resultSet.getString(7));
//                        preparedStatement2.setString(8, resultSet.getString(8));
//                        preparedStatement2.setString(9, resultSet.getString(9));
//                        preparedStatement2.setString(10, resultSet.getString(10));
//                        preparedStatement2.setString(11, resultSet.getString(11));
//                        preparedStatement2.setString(12, resultSet.getString(12));
//                        preparedStatement2.setString(13, resultSet.getString(13));

                        preparedStatement2.setString(1, resultSet.getString(1));
                        preparedStatement2.setString(2, resultSet.getString(2));
                        preparedStatement2.setString(3, resultSet.getString(3));
                        preparedStatement2.setString(4, resultSet.getString(4));

                        preparedStatement2.setFloat(5, resultSet.getFloat(5));
                        preparedStatement2.setFloat(6, resultSet.getFloat(6));
                        preparedStatement2.setFloat(7, resultSet.getFloat(7));
                        preparedStatement2.setFloat(8, resultSet.getFloat(8));
                        preparedStatement2.setFloat(9, resultSet.getFloat(9));
                        preparedStatement2.setFloat(10, resultSet.getFloat(10));
                        preparedStatement2.setFloat(11, resultSet.getFloat(11));
                        preparedStatement2.setFloat(12, resultSet.getFloat(12));

                        preparedStatement2.setInt(13, resultSet.getInt(13));

                        preparedStatement2.setFloat(14, resultSet.getFloat(14));

                        preparedStatement2.setFloat(15, resultSet.getFloat(15));
                        preparedStatement2.setFloat(16, resultSet.getFloat(16));
                        preparedStatement2.setFloat(17, resultSet.getFloat(17));
                        preparedStatement2.setFloat(18, resultSet.getFloat(18));
                        preparedStatement2.setFloat(19, resultSet.getFloat(19));

                        preparedStatement2.setString(20, resultSet.getString(20));
                        preparedStatement2.setString(21, resultSet.getString(21));
                        preparedStatement2.setString(22, resultSet.getString(22));
                        preparedStatement2.setString(23, resultSet.getString(23));
                        preparedStatement2.setString(24, resultSet.getString(24));
                        preparedStatement2.setString(25, resultSet.getString(25));
                        preparedStatement2.setString(26, resultSet.getString(26));
                        preparedStatement2.setString(27, resultSet.getString(27));
                        preparedStatement2.setString(28, resultSet.getString(28));
                        preparedStatement2.setString(29, resultSet.getString(29));

                        preparedStatement2.setFloat(30, resultSet.getFloat(30));
                        preparedStatement2.setFloat(31, resultSet.getFloat(31));
                        preparedStatement2.setFloat(32, resultSet.getFloat(32));
                        preparedStatement2.setFloat(33, resultSet.getFloat(33));
                        preparedStatement2.setFloat(34, resultSet.getFloat(34));
                        preparedStatement2.setFloat(35, resultSet.getFloat(35));

                        preparedStatement2.setString(36, resultSet.getString(36));
                        preparedStatement2.setString(37, resultSet.getString(37));
                        preparedStatement2.setString(38, resultSet.getString(38));

                        preparedStatement2.setInt(39, resultSet.getInt(39));
                        preparedStatement2.setString(40, resultSet.getString(40));
                        preparedStatement2.setInt(41, resultSet.getInt(41));
                        preparedStatement2.setString(42, resultSet.getString(42));
                        preparedStatement2.setInt(43, resultSet.getInt(43));
                        preparedStatement2.setFloat(44, resultSet.getFloat(44));
                        preparedStatement2.setFloat(45, resultSet.getFloat(45));
                        preparedStatement2.setString(46, resultSet.getString(46));
                        preparedStatement2.setString(47, resultSet.getString(47));
                        preparedStatement2.setFloat(48, resultSet.getFloat(48));
                        preparedStatement2.setInt(49, resultSet.getInt(49));
                        preparedStatement2.setInt(50, resultSet.getInt(50));
                        preparedStatement2.setString(51, resultSet.getString(51));
                        preparedStatement2.setString(52, resultSet.getString(52));
                        preparedStatement2.setString(53, resultSet.getString(53));
                        preparedStatement2.setString(54, resultSet.getString(54));
                        preparedStatement2.setString(55, resultSet.getString(55));
                        preparedStatement2.setString(56, resultSet.getString(56));
                        preparedStatement2.setString(57, resultSet.getString(57));
                        preparedStatement2.setString(58, resultSet.getString(58));
                        preparedStatement2.setString(59, resultSet.getString(59));
                        preparedStatement2.setString(60, resultSet.getString(60));
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
