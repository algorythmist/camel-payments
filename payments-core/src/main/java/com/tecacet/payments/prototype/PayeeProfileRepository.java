package com.tecacet.payments.prototype;

import com.tecacet.payments.model.Customer;
import com.tecacet.payments.model.PayeeProfile;

import java.util.List;

public interface PayeeProfileRepository {

    List<PayeeProfile> findPayeeProfiles(Customer customer);
}
