package com.tecacet.payments.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
@Getter
public class InvoiceProfile {

    @Delegate
    private final Invoice invoice;
    private final PayeeProfile payeeProfile;


    public Account getAccount() {
        return payeeProfile.getSourceAccount();
    }

}
