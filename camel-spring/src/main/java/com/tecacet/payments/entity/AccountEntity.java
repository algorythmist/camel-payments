package com.tecacet.payments.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
@Deprecated
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
public class AccountEntity extends BaseEntity {

    @ManyToOne
    private CustomerEntity customer;
    private BigDecimal minimumBalanceRequirement;
    private BigDecimal maximumWithdrawalLimit;

}
