package com.tecacet.payments.camel;

import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.function.Function;

@RequiredArgsConstructor
public class FunctionProcessor<I,O> implements Processor {

    private final Function<I, O> function;

    @Override
    public void process(Exchange exchange) {
        I input = (I) exchange.getIn().getBody();
        O output = function.apply(input);
        exchange.getMessage().setBody(output);
    }
}
