package com.tecacet.payments.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @ToString.Include
    @EqualsAndHashCode.Include
    private String accountIdentifier;
    private BigDecimal minimumBalanceRequirement;
    private BigDecimal maximumWithdrawalLimit;

}
