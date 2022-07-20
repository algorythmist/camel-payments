package com.tecacet.payments.repository;

import com.tecacet.payments.model.Payment;

public interface PaymentRepository {

    Payment save(Payment payment);
}
