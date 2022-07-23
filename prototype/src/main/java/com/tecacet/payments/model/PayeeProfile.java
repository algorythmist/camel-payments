package com.tecacet.payments.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class PayeeProfile {

    private Customer customer;
    private Payee payee;
    private int daysAhead;
    private Account sourceAccount;
    private PaymentMethod paymentMethod;

}
