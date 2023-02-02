package com.study.util.csv.easy_excel_util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.xmlbeans.impl.common.ReaderInputStream;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 * 读取csv文件,然后写入到数据库表中
 */

public class ExcelByEasyExcelUtil {


    public static void readCsv(String filePath, String version) throws Exception {

        File file = new File(filePath);
        if (file == null) {
            throw new IllegalArgumentException("file not exists");
        }
        InputStream in = new FileInputStream((File) file);
        if (version == null) {
            throw new IllegalArgumentException("version not exists");
        }

        // 输入文件的编码格式是GBK,所以InputStreamReader使用的GBK; 处理程序中的编码格式是UTF-8, 所以ReaderInputStream设置为UTF-8
        in = new ReaderInputStream(new InputStreamReader(in, "GBK"), "UTF-8");

        Connection connection = getConnection();

        String sql = "insert into tnb_supply_source_dbds(brand, site, commodity, alternative, odm_pn, measure, qty, supply_date, supply_month, version, job_id) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        LocalDate localDate = LocalDate.now();
        String jobId = "Tnb_Supply_Source_" + localDate.toString().replace("-", "") + "_" + UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        EasyExcel.read(in, new AnalysisEventListener<Map<Integer, String>>() {

            // head中存放标题字段,按照顺序存取
            private Map<Integer, String> head;
            // head2中存放第一行的数据, 按照顺序存取
            private Map<Integer, String> head2;
            // head3中存放第一行的数据, 按照顺序存取
            private Map<Integer, String> head3;
            // 记录标题中日期开始时对应列的数字 - 1
            private Integer qtyRowStar;
            private List<List<Object>> data = new ArrayList<>();

            @Override
            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                head = headMap;
                for (int i = 1; i < Integer.MAX_VALUE; i++) {
                    String s = head.get(i);
                    if (s == null) {
                        break;
                    }
                    if (s.equals("MEASURE")) {
                        qtyRowStar = i + 1;
                    }
                }
//                for (int i = qtyRowStar; i < Integer.MAX_VALUE; i++) {
//                    String s = head.get(i);
//                    if (s == null) {
//                        break;
//                    }
//                    LocalDate date = LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy/M/d"));
//                    head.put(i, date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//                }

            }

            Map<Integer, String> compressLine;
            String compressOdmPn = "";

            @Override
            public void invoke(Map<Integer, String> line, AnalysisContext context) {

                // csv文件数据重复，重复行全部跳过
                if (line.equals(compressLine)) {
                    return;
                } else {
                    compressLine = line;
                }

                // 读取的是第一行 ：context.readRowHolder().rowIndex == 1
                if (context.readRowHolder().getRowIndex() == 1) {
                    head2 = line;
                    // 结束第一场数据的处理
                    return;
                }

                // 读取的是第3行 ：context.readRowHolder().rowIndex == 3
                if (context.readRowHolder().getRowIndex() == 3) {
                    head3 = line;
                    // 结束第一场数据的处理
                    return;
                }

                // 过滤不是 02_Supply_Upload 的数据
                if (!"02_Supply_Upload".equals(line.get(23))) {
                    return;
                }

                // 去除汇总数据
                if (line.get(9).contains("/") || line.get(9).equals(compressOdmPn)) {
                    compressOdmPn = "";
                    return;
                } else
                    compressOdmPn = line.get(9);

                String brand = line.get(1);
                String site = line.get(2);
                String commodity = line.get(5);
                String alternative = line.get(8);
                String odm_pn = line.get(9);
                String measure = line.get(23);

                // 行专列操作
                for (int i = qtyRowStar; head.get(i) != null; i++) {

                    String qty = line.get(i);
                    // qty数据为0的数据不需要保存
                    if ("0".equals(qty))
                        continue;
                    String supply_date = head2.get(i).substring(0, 4) + "-" + head2.get(i).substring(4, 6) + "-" + head2.get(i).substring(6, 8);
                    String supply_month = head3.get(i).substring(0, 4) + "-" + head3.get(i).substring(4, 6);

                    data.add(Arrays.asList(
                            brand, site, commodity, alternative, odm_pn, measure, qty,
                            supply_date, supply_month, version, jobId
                    ));

                }

                if (data.size() > 1000) {
//                    insert(data, ps);
                    data.clear();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                if (!data.isEmpty()) {
//                    insert(data, ps);
                    data.clear();
                }
            }
        }).excelType(ExcelTypeEnum.CSV).sheet().doRead();

        ps.close();
        connection.commit();
        connection.close();
    }

    private void insert(List<List<Object>> data, PreparedStatement ps) {
        try {
            for (List<Object> datum : data) {
                for (int i = 0; i < datum.size(); i++) {
                    Object value = datum.get(i);
                    if (value instanceof BigDecimal) {
                        ps.setBigDecimal(i + 1, (BigDecimal) value);
                    } else {
                        ps.setString(i + 1, (String) value);
                    }
                }
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 自定义的Reader,用来将 \ 转化为 ,
    private class MyReader extends BufferedReader {
        public MyReader(Reader reader) {
            super(reader);
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            int read = super.read(cbuf, off, len);
            new String(cbuf);
            for (int i = 0; i < cbuf.length; i++) {
                if (cbuf[i] == '\t') {
                    cbuf[i] = ',';
                }
            }
            return read;
        }

        @Override
        public void close() throws IOException {
            super.close();
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@10.122.13.226:1521:tstmdmn1", "GSPLNB_SCPOMGR", "GSPLNB_SCPOMGR");
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
