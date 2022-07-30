package com.tecacet.payments.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@ToString
public class Account {

    private AccountIdentifier accountIdentifier;
    private BigDecimal balance;
    private BigDecimal minimumBalanceRequirement;
    private BigDecimal maximumWithdrawalLimit;

}
