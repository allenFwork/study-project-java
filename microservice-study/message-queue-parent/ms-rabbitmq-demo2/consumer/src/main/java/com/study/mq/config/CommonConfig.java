package com.study.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public DirectExchange errorMessageExchange() {
        // 定义一个异常消息处理的交换机
        return new DirectExchange("error.direct");
    }

    @Bean
    public Queue errorQueue() {
        // 定义一个异常消息处理的队列
        return new Queue("error.queue", true);
    }

    @Bean
    public Binding errorBinding(Queue errorQueue, DirectExchange errorMessageExchange) {
        return BindingBuilder.bind(errorQueue).to(errorMessageExchange).with("error");
    }

    @Bean // 如果自定义了Bean对象，那么此Bean对象会覆盖spring默认的MessageRecoverer对象（即RejectAndDontRequeueRecoverer对象）
    public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, "error.direct", "error");
    }

}
