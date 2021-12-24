package com.study.util.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.study.util.excel.easy_excel_util.entity.StoreInformation;
import com.study.util.excel.easy_excel_util.listener.StoreExcelListener;
import com.study.util.excel.handeler.CommentWriteHandler;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

public class EasyExcelUtilTest {

    @Test
    public void readExcel() {
        String fileName = "C:\\documents\\work\\temp\\來酷门店信息_整理.xlsx";


        // 不传参数使用
        EasyExcel.read(fileName, StoreInformation.class, new StoreExcelListener()).sheet().doRead();
        EasyExcel.read(fileName, StoreInformation.class, new AnalysisEventListener<StoreInformation>() {

            // 批量处理阈值
            private static final int BATCH_COUNT = 100;
            // 读取excel后封装的数据
            List<StoreInformation> list = new ArrayList<StoreInformation>(BATCH_COUNT);

            @Override
            public void invoke(StoreInformation storeInformation, AnalysisContext analysisContext) {
                list.add(storeInformation);
                if (list.size() >= BATCH_COUNT) {
                    // 业务处理 ...

                    // 清楚集合中的数据
                    list.clear();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }

        }).sheet().doRead();
    }

    @Test
    public void readExcel2() throws FileNotFoundException {

        String fileName = "C:\\documents\\work\\temp\\來酷门店信息_整理.xlsx";
        String fileName2 = "C:\\documents\\work\\temp\\來酷门店信息_整理2.xlsx";

        // 传参数使用
        HashMap<String, Object> params = new HashMap();
        params.put("key", "value");

        // 返回结果
        List<Map<String, String>> commentList = new ArrayList<>();
        params.put("commentList", commentList);

        EasyExcel.read(fileName, StoreInformation.class, new AnalysisEventListener<StoreInformation>() {

            // 读取excel后封装的数据
            List<StoreInformation> list = new ArrayList<StoreInformation>();

            @Override
            public void invoke(StoreInformation storeInformation, AnalysisContext analysisContext) {
                // 获取参数
                HashMap params = (HashMap) analysisContext.getCustom();
                String value = (String) params.get("key");

                List<Map<String, String>> commentList = (List<Map<String, String>>) params.get("commentList");
                String sheetName = analysisContext.readSheetHolder().getSheetName();
                int rowIndex = analysisContext.readRowHolder().getRowIndex() - 1;
                int colIndex = 2;
                commentList.add(CommentWriteHandler.createCommentMap(sheetName, rowIndex, colIndex, "第一条批注: " + rowIndex));

                list.add(storeInformation);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                // 获取参数
                HashMap params = (HashMap) analysisContext.getCustom();
                String value = (String) params.get("key");

                params.put("result", list);

            }

        }).customObject(params).sheet().doRead();

        commentList = (List<Map<String, String>>) params.get("commentList");
        List<StoreInformation> list = (List<StoreInformation>) params.get("result");

        FileOutputStream fileOutputStream = new FileOutputStream(fileName2);
        ExcelWriter excelWriter = EasyExcel.write(fileOutputStream).inMemory(Boolean.TRUE).registerWriteHandler(new CommentWriteHandler(commentList, "xlsx")).build();
        // 根据用户传入字段
        Set<String> includeColumnFiledNames = new HashSet<String>();
        includeColumnFiledNames.add("storeName");
        includeColumnFiledNames.add("storeCode");
        includeColumnFiledNames.add("dealerCode");
        includeColumnFiledNames.add("dealerName");
        includeColumnFiledNames.add("dealerType");
        includeColumnFiledNames.add("storeLongitude");
        includeColumnFiledNames.add("storeLatitude");
        // 设置sheetName、字段名
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").head(StoreInformation.class).includeColumnFiledNames(includeColumnFiledNames).build();
        excelWriter.write(list, writeSheet);
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }


}
