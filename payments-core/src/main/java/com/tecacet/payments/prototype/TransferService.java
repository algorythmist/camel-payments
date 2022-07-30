package com.tecacet.payments.prototype;

import com.tecacet.payments.model.Payment;

public interface TransferService {
    void executePayment(Payment payment);
}
