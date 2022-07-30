package com.tecacet.payments.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Invoice {

    @EqualsAndHashCode.Include
    private String invoiceIdentifier;
    private BigDecimal totalAmount;
    private BigDecimal minimumAmount;
    private LocalDate dueDate;

}
