package com.revolut.transfer.application;

import java.math.BigInteger;

import com.google.inject.Inject;

public class ThreadHandler{

    @Inject
    private AccountLocks accountLocks;

    public void manageThreads(final BigInteger accountID1, final BigInteger accountID2, final Runnable runnable) {
        final BigInteger lockFirst = accountID1.compareTo(accountID2) < 0 ? accountID1 : accountID2;
        final BigInteger lockSecond = accountID1.compareTo(accountID2) < 0 ? accountID2 : accountID1;

        synchronized (accountLocks.getLock(lockFirst)) {
            synchronized (accountLocks.getLock(lockSecond)) {
                runnable.run();
            }
        }
    }
}
