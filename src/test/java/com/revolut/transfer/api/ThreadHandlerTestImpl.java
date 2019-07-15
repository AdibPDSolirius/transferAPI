package com.revolut.transfer.api;

import java.math.BigInteger;

import com.revolut.transfer.application.ThreadHandler;

public class ThreadHandlerTestImpl extends ThreadHandler {

    public void manageThreads(final BigInteger accountID1, final BigInteger accountID2, final Runnable runnable) {
        runnable.run();
    }
}
