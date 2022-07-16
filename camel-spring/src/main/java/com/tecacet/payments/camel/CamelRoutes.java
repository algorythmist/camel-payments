package com.tecacet.payments.camel;

import com.tecacet.payments.entity.InvoiceEntity;
import com.tecacet.payments.entity.PayeeProfileEntity;
import com.tecacet.payments.entity.PaymentEntity;
import com.tecacet.payments.model.PaymentPayload;
import com.tecacet.payments.repository.CustomerRepository;
import com.tecacet.payments.repository.PayeeProfileRepository;
import com.tecacet.payments.repository.PaymentRepository;
import com.tecacet.payments.service.BalanceService;
import com.tecacet.payments.service.InvoiceService;
import com.tecacet.payments.service.PaymentService;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class CamelRoutes extends RouteBuilder {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PayeeProfileRepository payeeProfileRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public void configure() {

        from("direct:customers")
                .routeId("customers") //Give this route a name
                .bean(customerRepository, "findAllCustomers") //query all customers
                .split(simple("${body}")) //split the list: for each customer
                .parallelProcessing() // parallel process customers
                .bean(payeeProfileRepository, "findByCustomer") // find payee profiles for this customer
                .filter(simple("${body.size} != 0"))// filter out empty lists
                .split(simple("${body}")) // split the list : for each payee profile
                .multicast(new GroupedExchangeAggregationStrategy()) // Route each payee profile to three different services:
                .transform().body()// this returns the payee profile itself
                .bean(invoiceService, "findInvoices") // This queries all the invoices due for this profile
                .bean(balanceService, "getBalance") // This gets the account balance from the customer's account
                .end() // end of multicast: all three routes come together
                .bean(CamelRoutes.this, "combineExchanges") // combine the results from the serices into a single object
                .bean("paymentService", "payInvoices")
                .bean(paymentRepository, "saveAll"); // Save the payment objects
        //TODO: transfer service
    }

    public PaymentPayload combineExchanges(Exchange exchange) {
        List<Exchange> exchanges = (List<Exchange>) exchange.getMessage().getBody();
        PaymentPayload payload = new PaymentPayload();
        for (Exchange e : exchanges) {
            Object o = e.getMessage().getBody();
            if (o instanceof BigDecimal) {
                payload.setAccountBalance((BigDecimal) o);
            } else if (o instanceof PayeeProfileEntity) {
                payload.setProfile((PayeeProfileEntity) o);
            } else {
                payload.setInvoices((List<InvoiceEntity>) o);
            }
        }
        return payload;
    }

}