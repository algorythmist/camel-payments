package com.tecacet.payments.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
public class InvoiceEntity extends BaseEntity {

    public enum Status {
        DUE,
        PENDING,
        PAID
    }

    @ManyToOne
    private CustomerEntity customer;
    @ManyToOne
    private PayeeEntity payee;
    private BigDecimal totalAmount;
    private BigDecimal minimumAmount;
    private LocalDate dueDate;
    private Status status;
}
