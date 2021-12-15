package com.study.util.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.study.util.excel.entity.StoreInformation;
import com.study.util.excel.listener.StoreExcelListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EasyExcelUtilTest {

    @Test
    public void readExcel() {
        String fileName = "C:\\Users\\86131\\IdeaProjects\\study-project-java\\utils\\src\\main\\resources\\來酷门店信息_整理.xlsx";


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


        // 传参数使用
        HashMap<String, String> params = new HashMap();
        params.put("key", "value");
        EasyExcel.read(fileName, StoreInformation.class, new AnalysisEventListener<StoreInformation>() {

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
