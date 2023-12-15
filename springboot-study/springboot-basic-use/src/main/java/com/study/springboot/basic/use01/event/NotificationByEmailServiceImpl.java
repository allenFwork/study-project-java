package com.study.springboot.basic.use01.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Email通知服务层
 */
@Component
public class NotificationByEmailServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(NotificationByEmailServiceImpl.class);

    @EventListener
    public void sendMessage(UserRegisteredEvent event) {
        log.debug("spring容器管理的监听事件：{}", event);
        log.debug("发送邮件");
    }

}
