package com.tecacet.camel.spring.examples.config;

import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;


@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "employee-exchange";
    public static final String QUEUE = "employee-queue";

    public static final String ROUTING = "employee-routing";


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @Bean
    public Declarables directBinding() {
        boolean durable = true;
        boolean autoDelete = true;

        Queue queue = new Queue(QUEUE);

        DirectExchange exchange = new DirectExchange(EXCHANGE, durable, autoDelete);

        return new Declarables(
                queue,
                exchange,
                bind(queue).to(exchange).with(ROUTING));
    }
}
