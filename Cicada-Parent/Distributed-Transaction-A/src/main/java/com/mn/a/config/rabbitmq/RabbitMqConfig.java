package com.mn.a.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig
{
    static final String QUEUE_NAME = "transfer-account";

    @Bean
    public Queue confirmQueue()
    {
        return new Queue(QUEUE_NAME);
    }

}
