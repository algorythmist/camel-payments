package com.tecacet.camel.spring.examples.rabbit;

import com.tecacet.camel.spring.examples.config.RabbitConfig;
import com.tecacet.camel.spring.examples.model.Employee;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RabbitListener(queues = {RabbitConfig.QUEUE})
public class RabbitQueueListener {

    @RabbitHandler
    public String receiveAndRespond(@Payload Object employee) {
        //String message = new String(bytes, StandardCharsets.UTF_8);
        return  "hello!";
    }

}
