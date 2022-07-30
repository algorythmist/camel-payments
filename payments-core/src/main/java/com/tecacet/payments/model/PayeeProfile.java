package com.tecacet.payments.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PayeeProfile {

    private PayeeIdentifier payee;
    private int daysAhead;
    private AccountIdentifier sourceAccount;
    private PaymentMethod paymentMethod;

}
