package com.study.message.queue.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用 org.springframework.test.context.junit4.SpringRunner 类，RunWith固定用法
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqDemoApplicationTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test // BasicQueue
    public void testSimpleBasic() {
        // 队列名称
        String queueName = "simple.queue";
        // 消息
        String message = "Hello, Spring AMQP";
        // 发送消息
        rabbitTemplate.convertAndSend(queueName, message);
    }

    /**
     * workQueue
     * 向队列中不停发送消息，模拟消息堆积。
     */
    @Test
    public void testWorkBasic() throws InterruptedException {
        // 队列名称
        String queueName = "work.queue";
        // 消息
        String message = "Hello, message_";
        for (int i = 0; i < 50; i++) {
            // 发送消息(只能向队列中发送信息，却不能在队列不存在的情况下创建队列)
            rabbitTemplate.convertAndSend(queueName, message + i);
            Thread.sleep(20);
        }
        // 是因为上面睡眠了20毫秒，而Spring的RabbitMQ的消费者（监视器）在这段时间内已经能消费完队列中所有的消息
        System.out.println("testWorkBasic方法的逻辑结束了 ...");
    }

    @Test
    public void testFanoutExchange() throws InterruptedException {
        // 交换机名称
        String exchangeName = "test.fanout";
        // 消息
        String message = "hello, everyone!";
        rabbitTemplate.convertAndSend(exchangeName, "", message);
        // 为了等消费者消费完，所以让此线程休眠了20毫秒
        Thread.sleep(20);
    }

    @Test
    public void testDirectExchange() throws InterruptedException {
        // 交换机名称
        String exchangeName = "test.direct";
        // 消息
        String message = "红色警报！日本乱排核废水，导致海洋生物变异，惊现哥斯拉！";
        // 发送消息，指定了对应的routingKey为red
        rabbitTemplate.convertAndSend(exchangeName, "red", message);
        Thread.sleep(20);
    }

    @Test
    public void testTopicExchange() throws InterruptedException {
        // 交换机名称
        String exchangeName = "test.topic";
        // 消息
        String message = "喜报！孙悟空大战哥斯拉，胜!";
        // 发送消息，指定了对应的routingKey为red
//        rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
        rabbitTemplate.convertAndSend(exchangeName, "china.animals", message);
        Thread.sleep(20);
    }

    @Test
    public void testSendMap() throws InterruptedException {
        // 准备消息
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Jack");
        map.put("age", 18);
        // 发送消息
        rabbitTemplate.convertAndSend("object.queue", map);
        Thread.sleep(20);
    }
}
