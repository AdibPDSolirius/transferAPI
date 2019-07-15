package com.revolut.transfer.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

public class AccountBalance {


    private BigInteger id;
    private BigDecimal balance;

    public AccountBalance(BigInteger id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public boolean transferTo(final AccountBalance receiver, final BigDecimal amount) {
        if(!this.withdraw(amount)) {
            return false;
        }

        return receiver.deposit(amount);
    }

    private boolean withdraw(final BigDecimal amount) {

        if (this.getBalance().compareTo(amount) < 0) {
            return false;
        }

        this.balance = this.getBalance().subtract(amount);
        return true;
    }

    private boolean deposit(final BigDecimal amount) {
        this.balance = this.getBalance().add(amount);
        return true;
    }

    public BigInteger getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }
}
