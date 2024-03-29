package com.tecacet.payments.processor;

import com.tecacet.payments.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class PaymentsProcessor {

    public List<Payment> payInvoices(Collection<InvoiceProfile> invoices, Account account) {
        List<InvoiceProfile> invoicesByDueDate =
                invoices.stream().sorted(Comparator.comparing(InvoiceProfile::getDueDate)).toList();
        List<Payment> payments = new ArrayList<>();
        BigDecimal remainingBalance = account.getBalance();
        for (InvoiceProfile invoiceProfile : invoicesByDueDate) {
            Payment payment = payInvoice(invoiceProfile, account, remainingBalance);
            if (payment.getStatus() != Payment.Status.NOT_PAID) {
                remainingBalance = remainingBalance.subtract(payment.getAmount());
            }
            payments.add(payment);
        }
        return payments;
    }


    public Payment payInvoice(InvoiceProfile invoiceProfile, Account account, BigDecimal balance) {
        Invoice invoice = invoiceProfile.getInvoice();
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
                .payeeProfile(invoiceProfile.getPayeeProfile())
                .date(LocalDate.now())
                .build();
    }
}
