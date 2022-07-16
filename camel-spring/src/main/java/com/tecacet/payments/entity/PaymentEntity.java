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
public class PaymentEntity extends BaseEntity {

    @ManyToOne
    private InvoiceEntity invoice;
    @ManyToOne
    private PayeeProfileEntity payeeProfile;
    private BigDecimal amount;
    private LocalDate date;
}
