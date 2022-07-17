package com.tecacet.payments.core;

import com.tecacet.payments.model.Account;
import com.tecacet.payments.model.Invoice;
import com.tecacet.payments.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentsProcessor {

    public List<Payment> payInvoices(Account account, Collection<Invoice> invoices, BigDecimal balance) {
        List<Invoice> invoicesByDueDate =
                invoices.stream().sorted(Comparator.comparing(Invoice::getDueDate)).toList();
        List<Payment> payments = new ArrayList<>();
        BigDecimal remainingBalance = balance;
        for (Invoice invoice : invoicesByDueDate) {
            Payment payment = payInvoice(account, invoice, remainingBalance);
            if (payment.getStatus() != Payment.Status.NOT_PAID) {
                remainingBalance = remainingBalance.subtract(payment.getAmount());
            }
            payments.add(payment);
        }
        return payments;
    }


    public Payment payInvoice(Account account, Invoice invoice, BigDecimal balance) {
        BigDecimal availableBalance = balance.subtract(account.getMinimumBalanceRequirement());
        availableBalance = availableBalance.min(account.getMaximumWithdrawalLimit());
        BigDecimal paymentAmount = BigDecimal.ZERO;
        Payment.Status status;
        String message = "";
        if (availableBalance.compareTo(invoice.getMinimumAmount()) < 0 ) {
            //Invoice cannot be paid
            status = Payment.Status.NOT_PAID;
            message = "Balance cannot cover minimum payment due.";
        } else if (availableBalance.compareTo(invoice.getTotalAmount()) < 0) {
            //Invoice cannot be paid in full
            status = Payment.Status.PARTIALLY_PAID;
            message = "Balance cannot cover the full amount.";
            paymentAmount = availableBalance;
        } else {
            status = Payment.Status.FULLY_PAID;
            paymentAmount = invoice.getTotalAmount();
        }
        return Payment.builder()
                .amount(paymentAmount)
                .status(status)
                .message(message)
                .invoice(invoice)
                .date(LocalDate.now())
                .build();
    }
}
