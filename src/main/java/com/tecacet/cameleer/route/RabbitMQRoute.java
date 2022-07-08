package com.tecacet.cameleer.route;

import com.tecacet.cameleer.model.Employee;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQRoute extends RouteBuilder {

    @Override
    public void configure()  {
        JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);

        from("direct:start")
                .id("employees").marshal(jsonDataFormat)
                .to("rabbitmq:employees?queue=employee")
                .end();

    }
}
