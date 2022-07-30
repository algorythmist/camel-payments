package com.tecacet.payments.prototype;

import com.tecacet.payments.model.CustomerIdentifier;
import com.tecacet.payments.model.PayeeProfile;

import java.util.List;

public interface PayeeProfileRepository {

    List<PayeeProfile> findPayeeProfiles(CustomerIdentifier customerIdentifier);
}
