package com.tecacet.camel.spring.examples.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class GreetingsFileRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("direct:start-greeting")
                .routeId("greetings-route")
                .setBody(constant("Hello Potato!"))
                .to("file:output");
    }
}