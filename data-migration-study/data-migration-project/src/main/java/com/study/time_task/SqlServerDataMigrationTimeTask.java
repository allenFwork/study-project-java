package com.study.time_task;

import com.study.mapper.lec.interfaces.LenovoSkujcsjSpkcBackupMapper;
import com.study.mapper.lec2.interfaces.LenovoSkujcsjSpkcJavaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class SqlServerDataMigrationTimeTask {

    @Autowired
    private LenovoSkujcsjSpkcJavaMapper lenovoSkujcsjSpkcJavaMapper;

    @Async // 异步,即从线程池中获取线程执行该定时任务，启用该注解,需要在主类中使用@EnableAsync
    @Scheduled(cron = "30 4 15 * *  ?")
    public void initDataClean() {
        long startTime = System.currentTimeMillis();
        log.info("定时任务||XXX开始||startTime={}", new Date());
        try {
            // 清空表数据
            lenovoSkujcsjSpkcJavaMapper.dropTable();
            // 1.获取源数据

            // 2.处理源数据

            // 3.插入数据到表中

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        log.info("定时任务||XXX结束||startTime={}", new Date());
        log.info("定时任务||花费时间||time={}", (endTime - startTime) + "秒");
    }

}
