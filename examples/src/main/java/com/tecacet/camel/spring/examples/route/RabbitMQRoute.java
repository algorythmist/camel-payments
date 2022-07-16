package com.tecacet.camel.spring.examples.route;

import com.tecacet.camel.spring.examples.config.RabbitConfig;
import com.tecacet.camel.spring.examples.model.Employee;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQRoute extends RouteBuilder {

    @Override
    public void configure()  {
        JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);

        from("direct:start-rabbit")
                .id("simple").marshal(jsonDataFormat)
                .to(String.format("rabbitmq:%s?routingKey=%s", RabbitConfig.EXCHANGE, RabbitConfig.ROUTING))
                .end();

    }
}
