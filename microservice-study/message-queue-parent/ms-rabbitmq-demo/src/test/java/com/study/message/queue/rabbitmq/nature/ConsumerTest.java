package com.study.message.queue.rabbitmq.nature;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class ConsumerTest {

    @Test
    public void consumeTest() throws IOException, TimeoutException {
        ConsumerDemo.consume();
    }
}
