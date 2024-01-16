package com.study.message.queue.rabbitmq;

import com.study.message.queue.rabbitmq.nature.PublisherDemo;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PublisherTest {

    @Test
    public void sendMessage() throws IOException, TimeoutException {
        PublisherDemo.publishMessage();
    }
}
