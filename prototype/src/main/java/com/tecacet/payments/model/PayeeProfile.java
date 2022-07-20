package com.tecacet.payments.model;

import lombok.Data;

@Data
public class PayeeProfile {

    public enum PaymentMethod {
        CHECK,
        ACH_TRANSFER,
        STRIPE
    }

    private Customer customer;
    private Payee payee;
    private int daysAhead;
    private Account sourceAccount;
    private PaymentMethod paymentMethod;

}
