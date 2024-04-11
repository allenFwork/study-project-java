package com.study.jvm.actual_combat.practice_demo.oom.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SpringRabbitListener {

    @RabbitListener(queues = "queue1", concurrency = "10")
    public void listenSimpleQueue(String msg) throws InterruptedException {
        System.out.println(msg);
        Thread.sleep(30 * 1000);
    }

}
