package com.study.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CommonConfig implements ApplicationContextAware {

    /**
     * 设置RabbitMQ的生产者消息确认，Publish Return 确认：
     * 该回调返回是在消息到达交换机了，并且消息向队列传递时失败，此时RabbitMQ服务端会向生产者发送两个回调消息：
     * 1.生产者ack （此回调消息不会在这里处理，会在对应的发送消息时配置的回调方法中处理）
     * 2.publish return消息，即会调用下面的returnCallback方法
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取RabbitTemplate
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        // 设置ReturnCallback
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            // 判断是否为延迟消息

            // 投递失败，记录日志
            log.error("消息发送到队列失败，应答码{}，原因{}，交换机{}，路由键{},消息{}", replyCode, replyText, exchange, routingKey, message.toString());
            // 如果有业务需要，可以重发消息
        });
    }

    @Bean
    public DirectExchange simpleExchange(){
        // 三个参数：交换机名称、是否持久化、当没有queue与其绑定时是否自动删除
        return new DirectExchange("simple.direct", true, false);
    }
}
