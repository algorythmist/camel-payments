package com.tecacet.payments.model;

import com.tecacet.payments.entity.InvoiceEntity;
import com.tecacet.payments.entity.PayeeProfileEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PaymentPayload {

    private PayeeProfileEntity profile;
    private List<InvoiceEntity> invoices;
    private BigDecimal accountBalance;
}
