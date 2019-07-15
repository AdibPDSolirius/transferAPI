package com.revolut.transfer.data;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import com.revolut.transfer.domain.AccountBalance;

@Singleton
class Database {

    private final Map<BigInteger, AccountBalance> accounts = new HashMap<>();

    AccountBalance getAccountBalance(final BigInteger id) {
        final AccountBalance accountBalance = accounts.get(id);

        if(accountBalance == null) {
            return null;
        }

        return new AccountBalance(accountBalance.getId(), accountBalance.getBalance());
    }

    void saveAccountBalance(AccountBalance account) {
        accounts.put(account.getId(), account);
    }
}
