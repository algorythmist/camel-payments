package com.tecacet.payments.prototype;

import com.tecacet.payments.model.Account;
import com.tecacet.payments.model.AccountIdentifier;

import java.math.BigDecimal;

public interface AccountRepository {

    Account getAccount(AccountIdentifier accountIdentifier);
}
