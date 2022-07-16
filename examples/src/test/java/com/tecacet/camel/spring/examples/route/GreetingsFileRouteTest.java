package com.tecacet.camel.spring.examples.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@CamelSpringBootTest
@MockEndpoints("file:output")
class GreetingsFileRouteTest {

    @Autowired
    private ProducerTemplate template;

    @EndpointInject("mock:file:output")
    private MockEndpoint mock;

    @Test
    void whenSendBody_thenGreetingReceivedSuccessfully() throws InterruptedException {
        mock.expectedBodiesReceived("Hello Potato!");
        template.sendBody("direct:start-greeting", null);
        mock.assertIsSatisfied();
    }
}