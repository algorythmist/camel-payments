package com.tecacet.payments.service;

import com.tecacet.payments.model.Account;
import com.tecacet.payments.model.AccountIdentifier;

public interface AccountService {

    Account getAccount(AccountIdentifier accountIdentifier);
}
