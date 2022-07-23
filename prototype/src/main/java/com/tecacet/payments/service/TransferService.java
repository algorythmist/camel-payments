package com.tecacet.payments.service;

import com.tecacet.payments.model.Payment;
import com.tecacet.payments.model.PaymentMethod;

public interface TransferService {
    void executePayment(Payment payment);
}
