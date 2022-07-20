package com.tecacet.payments.repository;

import com.tecacet.payments.model.*;

import java.util.List;

public interface PayeeProfileRepository {

    List<PayeeProfile> findPayeeProfiles(Customer customer);
}
