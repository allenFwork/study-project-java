package com.study.springboot.basic.use01.event;

import org.springframework.context.ApplicationEvent;

/**
 * 定义用户注册事件，交由Spring容器来监听
 */
public class UserRegisteredEvent extends ApplicationEvent {
    public UserRegisteredEvent(Object source) {
        super(source);
    }
}
