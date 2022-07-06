package com.example.main;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

class FileProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        System.out.println("Processing..." + exchange.getExchangeId());
    }
}

class SimpleRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        from("file:input")
                .process(new FileProcessor())
                .to("file:output");
    }

}


public class MoveFilesDemo {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        SimpleRouteBuilder routeBuilder = new SimpleRouteBuilder();
        context.addRoutes(routeBuilder);
        context.start();
        Thread.sleep(51000);
        context.stop();
    }
}
