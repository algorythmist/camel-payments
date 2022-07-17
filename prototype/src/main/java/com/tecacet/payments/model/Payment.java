package com.tecacet.payments.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@ToString
public class Payment {

    public enum Status {
        NOT_PAID, PARTIALLY_PAID, FULLY_PAID
    }

    private Invoice invoice;
    private BigDecimal amount;
    private LocalDate date;
    private Status status;
    private String message;
}
