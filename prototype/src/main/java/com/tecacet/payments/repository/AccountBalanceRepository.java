package com.tecacet.payments.repository;

import com.tecacet.payments.model.Account;

import java.math.BigDecimal;

public interface AccountBalanceRepository {

    BigDecimal getAccountBalance(Account account);
}
