package com.tecacet.payments.processor;

import com.tecacet.payments.model.AccountIdentifier;
import com.tecacet.payments.model.CustomerIdentifier;
import com.tecacet.payments.model.PayeeIdentifier;

import java.util.UUID;

public class UniversalIdentifier implements AccountIdentifier, CustomerIdentifier, PayeeIdentifier {

    private final UUID id;

    public  UniversalIdentifier() {
        this.id = UUID.randomUUID();
    }
}
