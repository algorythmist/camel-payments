package com.tecacet.payments.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@ToString
public class Account {

    private BigDecimal minimumBalanceRequirement;
    private BigDecimal maximumWithdrawalLimit;

}
