package com.tecacet.payments;

import com.tecacet.payments.model.Account;
import com.tecacet.payments.model.AccountIdentifier;
import com.tecacet.payments.service.AccountService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockAccountService implements AccountService  {

    private final Map<AccountIdentifier, Account> accounts = new HashMap<>();

    @Override
    public Account getAccount(AccountIdentifier accountIdentifier) {
        return null;
    }

    public void save(Account account) {
        accounts.put(account.getAccountIdentifier(), account);
    }

    public void saveAll(Collection<Account> accounts) {
        accounts.forEach(this::save);
    }
}
