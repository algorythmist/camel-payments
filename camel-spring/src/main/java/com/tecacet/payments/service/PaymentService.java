package com.tecacet.payments.service;


import com.tecacet.payments.entity.AccountEntity;
import com.tecacet.payments.entity.InvoiceEntity;
import com.tecacet.payments.entity.PayeeProfileEntity;
import com.tecacet.payments.entity.PaymentEntity;
import com.tecacet.payments.model.PaymentPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    public List<PaymentEntity> payInvoices(List<PaymentPayload> payloads) {
        return payloads.stream().map(this::payPayeeInvoices).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<PaymentEntity> payPayeeInvoices(PaymentPayload payload) {
        var invoices = payload.getInvoices();
        var profile = payload.getProfile();
        var balance = payload.getAccountBalance();
        return invoices.stream().sorted(Comparator.comparing(InvoiceEntity::getDueDate))
                .map(invoice -> payInvoice(profile, invoice, balance))
                .collect(Collectors.toList());
    }

    public PaymentEntity payInvoice(PayeeProfileEntity profile, InvoiceEntity invoice, BigDecimal balance) {
        AccountEntity account = profile.getSourceAccount();
        BigDecimal availableBalance = balance.subtract(account.getMinimumBalanceRequirement());
        availableBalance = availableBalance.min(account.getMaximumWithdrawalLimit());
        if (availableBalance.compareTo(invoice.getMinimumAmount()) < 0 ) {
            //TODO: Problem. Invoice cannot be paid
            return null;
        }
        //TODO: keep track of available balance
        BigDecimal paymentAmount;
        if (availableBalance.compareTo(invoice.getTotalAmount()) < 0) {
            //TODO: Invoice cannot be paid in full
            paymentAmount = availableBalance;
        } else {
            paymentAmount = invoice.getTotalAmount();
        }
        return PaymentEntity.builder()
                .payeeProfile(profile)
                .amount(paymentAmount)
                .invoice(invoice)
                .date(LocalDate.now())
                .build();
    }

}
