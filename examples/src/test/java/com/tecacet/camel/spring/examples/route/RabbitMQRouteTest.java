package com.tecacet.camel.spring.examples.route;

import com.tecacet.camel.spring.examples.model.Employee;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RabbitMQRouteTest {

    @Autowired
    private ProducerTemplate template;

    @Test
    void test() {
        Employee employee = new Employee();
        String reply = template.requestBody("direct:start-rabbit", employee, String.class);
        //String reply = template.requestBody("direct:start-rabbit", "Hello", String.class);
        assertEquals("\"hello!\"", reply);
    }
}