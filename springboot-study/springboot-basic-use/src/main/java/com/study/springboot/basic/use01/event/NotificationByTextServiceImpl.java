package com.study.springboot.basic.use01.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 短信通知服务层
 */
@Component
public class NotificationByTextServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(NotificationByTextServiceImpl.class);

    @EventListener
    public void sendMessage(UserRegisteredEvent event) {
        log.debug("spring容器管理的监听事件：{}", event);
        log.debug("发送短信");
        if (true) {
            throw new RuntimeException("测试异常问题");
        }
    }

}
