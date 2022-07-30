package com.tecacet.payments.model;


import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class BankAccountIdentifier implements AccountIdentifier {

    private final String bankId;
    private final String accountId;
}
