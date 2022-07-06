package com.example.main;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

class SimpleRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        from("file:input").to("file:output");
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
