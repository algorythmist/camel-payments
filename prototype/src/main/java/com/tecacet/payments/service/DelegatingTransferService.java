package com.tecacet.payments.service;

import com.tecacet.payments.model.Payment;
import com.tecacet.payments.model.PaymentMethod;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DelegatingTransferService implements TransferService {

    private final TransferService checkService;
    private final TransferService achService;
    private final TransferService stripeService;

    private final InvoiceRepository invoiceRepository;

    @Override
    public void executePayment(Payment payment) {
        PaymentMethod paymentMethod = payment.getPayeeProfile().getPaymentMethod();
        switch (paymentMethod) {
            case CHECK -> checkService.executePayment(payment);
            case ACH_TRANSFER -> achService.executePayment(payment);
            case STRIPE -> stripeService.executePayment(payment);
        }
    }
}
