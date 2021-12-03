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

public class SqlServerApplicationStart3 {

    static DataBaseConnectPool dataBaseConnectPool1 = new DataBaseConnectPool();
    static DataBaseConnectPool2 dataBaseConnectPool2 = new DataBaseConnectPool2();

    public static void main(String[] args) {

        // 数据库中总记录数
        long rows = 18040652L;

        // 核心线程数
        int kThreads = Runtime.getRuntime().availableProcessors() * 2;
        // 任务数(这里需要求一下平均每个任务需要执行的任务id大小是多少，实际测试中，20-30w 快则 2秒，慢则3-5秒，这个阈值是比较理想的 也就是 总记录数/任意数 等到想要的平均任务数)
//        Long pageSize = (rows / 8000) + 1;
        Long pageSize = 18040652L / (kThreads - 1);

        // 线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(kThreads);

        // 数据起始位
        Long pageNo = 0L;

        System.out.println("结束时间：" + DateUtil.toDateString(new Date(), DateUtil.DATE_PATTERN2));
        // 存放线程执行结果
        List<Future> list = new Vector<>();
        // 执行多少次任务 = 结束位置不小于总记录数
        for (Long i = 0l; i < rows; i = pageNo) {
            Connection connection1 = dataBaseConnectPool1.getConnection();
            Connection connection2 = dataBaseConnectPool2.getConnection();
            final Future submit = threadPool.submit(new DataThread3(pageNo, pageSize, connection1, connection2));
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
    static class DataThread3 implements Callable {

        private Connection connection1;
        private Connection connection2;
        private Long pageNo, pageSize;

        public DataThread3(Long pageNo, Long pageSize, Connection connection1, Connection connection2) {
            this.connection1 = connection1;
            this.connection2 = connection2;
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            System.out.println("pageNo:" + pageNo + ",pageSize:" + pageSize);
        }

        @Override
        public Object call() throws Exception {
            System.out.println("开始执行");
            connection1.setAutoCommit(true);
            connection2.setAutoCommit(true);
            try {
                PreparedStatement preparedStatement = connection1.prepareStatement("SELECT * FROM Tactical_Offline..sc_material_master_data_rt_cml ORDER BY BU OFFSET ? rows fetch next ? rows only");
                preparedStatement.setLong(1, pageNo);
                preparedStatement.setLong(2, pageSize);
                ResultSet resultSet = preparedStatement.executeQuery();
                PreparedStatement preparedStatement2 =
                        connection2.prepareStatement("INSERT INTO HAWK_TACTICAL..sc_material_master_data_rt_cml_copy " +
                                "(matnr,mtart,maktx_en,maktx_zh,country_flag,prdha,vtext,zlcod,zucod,extwg,zeinr,matkl,ean11,numtp,normt,brgew,gewei,ntgew,volum,voleh,groes,atwrt_fru,atwrt_bom,zqd_qualification,zad_announce,zeowdate,update_timestamp,material_category,form_factor,prgrp,wgbez,color,mrom,mram,msim,scode,buyer,machine_code,ludp_update_timestamp) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
                    preparedStatement2.setString(10, resultSet.getString(10));
                    preparedStatement2.setString(11, resultSet.getString(11));
                    preparedStatement2.setString(12, resultSet.getString(12));
                    preparedStatement2.setString(13, resultSet.getString(13));
                    preparedStatement2.setString(14, resultSet.getString(14));
                    preparedStatement2.setString(15, resultSet.getString(15));
                    preparedStatement2.setString(16, resultSet.getString(16));
                    preparedStatement2.setString(17, resultSet.getString(17));
                    preparedStatement2.setString(18, resultSet.getString(18));
                    preparedStatement2.setString(19, resultSet.getString(19));
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
                    preparedStatement2.setString(30, resultSet.getString(30));
                    preparedStatement2.setString(31, resultSet.getString(31));
                    preparedStatement2.setString(32, resultSet.getString(32));
                    preparedStatement2.setString(33, resultSet.getString(33));
                    preparedStatement2.setString(34, resultSet.getString(34));
                    preparedStatement2.setString(35, resultSet.getString(35));
                    preparedStatement2.setString(36, resultSet.getString(36));
                    preparedStatement2.setString(37, resultSet.getString(37));
                    preparedStatement2.setString(38, resultSet.getString(38));
                    preparedStatement2.setString(39, resultSet.getString(39));
                    preparedStatement2.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dataBaseConnectPool1.releaseConnection(connection1);
                dataBaseConnectPool2.releaseConnection(connection2);
            }
            return "执行成功";
        }
    }

}
