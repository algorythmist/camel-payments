package com.tecacet.payments.service;

import com.tecacet.payments.entity.AccountEntity;
import com.tecacet.payments.entity.PayeeProfileEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface BalanceService {

    BigDecimal getAccountBalance(AccountEntity account);

    default BigDecimal getBalance(PayeeProfileEntity profile) {
        return getAccountBalance(profile.getSourceAccount());
    }
}
