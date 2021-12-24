package com.study.util.excel.easy_excel_util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.study.util.excel.easy_excel_util.entity.StoreInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EasyExcelUtil {

    public static void use(String filePath, Class clazz) {

    }

    public static void useByParam(String filePath, Object params) {
        EasyExcel.read(filePath, StoreInformation.class, new AnalysisEventListener<StoreInformation>() {

            // 批量处理阈值
            private static final int BATCH_COUNT = 100;
            // 读取excel后封装的数据
            List<StoreInformation> list = new ArrayList<StoreInformation>(BATCH_COUNT);

            @Override
            public void invoke(StoreInformation storeInformation, AnalysisContext analysisContext) {
                // 获取参数
                HashMap params = (HashMap) analysisContext.getCustom();
                String value = (String) params.get("key");

                list.add(storeInformation);
                if (list.size() >= BATCH_COUNT) {
                    // 业务处理 ...

                    // 清楚集合中的数据
                    list.clear();
                }

            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                // 获取参数
                HashMap params = (HashMap) analysisContext.getCustom();
                String value = (String) params.get("key");

            }

        }).customObject(params).sheet().doRead();
    }

}