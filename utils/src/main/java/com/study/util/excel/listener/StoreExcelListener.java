package com.study.util.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.study.util.excel.entity.StoreInformation;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StoreExcelListener extends AnalysisEventListener<StoreInformation> {

    // 批量处理阈值
    private static final int BATCH_COUNT = 100;
    // 读取excel后封装的数据
    List<StoreInformation> list = new ArrayList<StoreInformation>(BATCH_COUNT);

    @Override
    public void invoke(StoreInformation storeInformation, AnalysisContext analysisContext) {
//        log.info("解析到一条数据:{}", JSON.toJSONString(StoreInformation));
        list.add(storeInformation);
        if (list.size() >= BATCH_COUNT) {
            // 业务处理 ...

            // 清楚集合中的数据
            list.clear();
        }
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 业务处理...

        log.info("所有数据解析完成！");
    }

}
