package com.study.jvm.actual_combat.practice_demo.oom.task;

import com.study.jvm.actual_combat.practice_demo.oom.pojo.ArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.study.jvm.actual_combat.practice_demo.oom.config.ThreadPoolTaskConfig.BUFFER_QUEUE;

@Component
public class ArticleSaveTask {
    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    // 添加了 @PostConstruct 注解，那么ArticleSaveTask类在Spring的初始化过程中会调用pullArticleTask方法
    @PostConstruct
    public void pullArticleTask() {
        for (int i = 0; i < 50; i++) {
            threadPoolTaskExecutor.submit((Runnable) () -> {
                while (true) {
                    try {
                        ArticleDto data = BUFFER_QUEUE.take();
                        /**
                         * 获取到队列中的数据之后，调用第三方接口审核数据，但是此时网络出现问题，
                         * 第三方接口长时间没有响应，此处使用休眠来模式30秒
                         */
                        Thread.sleep(30 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
