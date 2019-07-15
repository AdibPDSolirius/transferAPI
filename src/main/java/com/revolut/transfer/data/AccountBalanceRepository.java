package com.revolut.transfer.data;

import java.math.BigInteger;

import com.google.inject.Inject;
import com.revolut.transfer.domain.AccountBalance;

public class AccountBalanceRepository {

    @Inject
    Database database;

    public AccountBalance findAccountBalanceByID(final BigInteger id) {
        return database.getAccountBalance(id);
    }

    public void saveAccountBalance(final AccountBalance account) {
        database.saveAccountBalance(account);
    }
}
