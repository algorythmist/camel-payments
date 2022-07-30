package com.tecacet.payments.service;

import com.tecacet.payments.model.Payment;

public interface TransferService {
    void executePayment(Payment payment);
}
