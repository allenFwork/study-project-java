package com.study.springboot.basic.use01.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 用户注册服务层
 */
@Component
public class UserRegisterServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(UserRegisterServiceImpl.class);

    @Autowired
    private ApplicationEventPublisher context;

    public void register() {
        log.debug("执行用户注册逻辑");
        /*
         * 发布用户注册事件,注意点：线性执行，必须等待监听的方法全部执行完，才会向下执行
         */
        context.publishEvent(new UserRegisteredEvent(this));
        log.debug("执行用户注册结束");
    }

}
