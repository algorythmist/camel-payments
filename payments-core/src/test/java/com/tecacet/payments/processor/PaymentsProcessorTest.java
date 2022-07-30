package com.tecacet.payments.processor;

import com.tecacet.payments.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentsProcessorTest {

    private final PaymentsProcessor paymentsProcessor = new PaymentsProcessor();

    @Test
    void payInvoice_notEnough() {
        AccountIdentifier accountIdentifier = new UniversalIdentifier();
        Account account = Account.builder()
                .accountIdentifier(accountIdentifier)
                .maximumWithdrawalLimit(BigDecimal.valueOf(1000))
                .minimumBalanceRequirement(BigDecimal.valueOf(100))
                .build();
        Invoice invoice = Invoice.builder()
                .minimumAmount(BigDecimal.valueOf(50))
                .totalAmount(BigDecimal.valueOf(700))
                .build();
        PayeeProfile payeeProfile = PayeeProfile.builder()
                .sourceAccount(accountIdentifier)
                .build();
        InvoiceProfile invoiceProfile = new InvoiceProfile(invoice, payeeProfile);
        Payment payment = paymentsProcessor.payInvoice(invoiceProfile, account, BigDecimal.valueOf(40));
        assertEquals(Payment.Status.NOT_PAID, payment.getStatus());
        assertEquals("Balance cannot cover minimum payment due.", payment.getMessage());
    }

    @Test
    void payInvoice_partial() {
        AccountIdentifier accountIdentifier = new UniversalIdentifier();
        Account account = Account.builder()
                .accountIdentifier(accountIdentifier)
                .maximumWithdrawalLimit(BigDecimal.valueOf(1000))
                .minimumBalanceRequirement(BigDecimal.valueOf(100))
                .build();
        Invoice invoice = Invoice.builder()
                .minimumAmount(BigDecimal.valueOf(50))
                .totalAmount(BigDecimal.valueOf(1200))
                .build();
        PayeeProfile payeeProfile = PayeeProfile.builder()
                .sourceAccount(accountIdentifier)
                .build();
        InvoiceProfile invoiceProfile = new InvoiceProfile(invoice, payeeProfile);
        Payment payment = paymentsProcessor.payInvoice(invoiceProfile, account, BigDecimal.valueOf(1500));
        assertEquals(Payment.Status.PARTIALLY_PAID, payment.getStatus());
        assertEquals("Balance cannot cover the full amount.", payment.getMessage());
        assertEquals(BigDecimal.valueOf(1000), payment.getAmount());
    }

    @Test
    void payInvoice_full() {
        AccountIdentifier accountIdentifier = new UniversalIdentifier();
        Account account = Account.builder()
                .accountIdentifier(accountIdentifier)
                .maximumWithdrawalLimit(BigDecimal.valueOf(2000))
                .minimumBalanceRequirement(BigDecimal.valueOf(100))
                .build();
        Invoice invoice = Invoice.builder()
                .minimumAmount(BigDecimal.valueOf(50))
                .totalAmount(BigDecimal.valueOf(1200))
                .build();
        PayeeProfile payeeProfile = PayeeProfile.builder()
                .sourceAccount(accountIdentifier)
                .build();
        InvoiceProfile invoiceProfile = new InvoiceProfile(invoice, payeeProfile);
        Payment payment = paymentsProcessor.payInvoice(invoiceProfile, account, BigDecimal.valueOf(1500));
        assertEquals(Payment.Status.FULLY_PAID, payment.getStatus());
        assertEquals(BigDecimal.valueOf(1200), payment.getAmount());
    }

    @Test
    void payInvoices() {
        AccountIdentifier accountIdentifier = new UniversalIdentifier();
        Account account = Account.builder()
                .accountIdentifier(accountIdentifier)
                .maximumWithdrawalLimit(BigDecimal.valueOf(5000))
                .minimumBalanceRequirement(BigDecimal.valueOf(100))
                .balance(BigDecimal.valueOf(2000))
                .build();

        PayeeProfile payeeProfile = PayeeProfile.builder()
                .sourceAccount(accountIdentifier)
                .build();

        Invoice invoice3 = Invoice.builder()
                .invoiceIdentifier("invoice3")
                .dueDate(LocalDate.of(2022, 10, 20))
                .minimumAmount(BigDecimal.valueOf(100))
                .totalAmount(BigDecimal.valueOf(200))
                .build();
        Invoice invoice1 = Invoice.builder()
                .invoiceIdentifier("invoice1")
                .dueDate(LocalDate.of(2022, 9, 21))
                .minimumAmount(BigDecimal.valueOf(1000))
                .totalAmount(BigDecimal.valueOf(1200))
                .build();
        Invoice invoice2 = Invoice.builder()
                .invoiceIdentifier("invoice2")
                .dueDate(LocalDate.of(2022, 10, 14))
                .minimumAmount(BigDecimal.valueOf(50))
                .totalAmount(BigDecimal.valueOf(1500))
                .build();
        List<Payment> payments = paymentsProcessor.payInvoices(
                Stream.of(invoice3, invoice1, invoice2)
                        .map(invoice -> new InvoiceProfile(invoice, payeeProfile))
                        .toList(), account);
        assertEquals(3, payments.size());
        Payment payment1 = payments.get(0);
        assertEquals(invoice1, payment1.getInvoice());
        assertEquals(Payment.Status.FULLY_PAID, payment1.getStatus());
        Payment payment2 = payments.get(1);
        assertEquals(invoice2, payment2.getInvoice());
        assertEquals(Payment.Status.PARTIALLY_PAID, payment2.getStatus());
        Payment payment3 = payments.get(2);
        assertEquals(invoice3, payment3.getInvoice());
        assertEquals(Payment.Status.NOT_PAID, payment3.getStatus());
    }
}