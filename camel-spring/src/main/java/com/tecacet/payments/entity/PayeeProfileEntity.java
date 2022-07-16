package com.tecacet.payments.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
public class PayeeProfileEntity extends BaseEntity {

    public enum PaymentMethod {
        CHECK,
        ACH_TRANSFER,
        STRIPE
    }

    @ManyToOne
    private CustomerEntity customer;
    @ManyToOne
    private PayeeEntity payee;
    @ManyToOne
    private AccountEntity sourceAccount;
    private int daysAhead;
    private PaymentMethod paymentMethod;

}
