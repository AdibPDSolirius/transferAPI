package com.revolut.transfer.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

@RunWith(JUnit4ClassRunner.class)
public class AccountBalanceTest {

    @Test
    public void shouldReturnTrueAndMoneyIsTransferredWhenSenderHasEnoughMoney() {
        final AccountBalance sender = new AccountBalance(BigInteger.ZERO, BigDecimal.valueOf(100));
        final AccountBalance receiver = new AccountBalance(BigInteger.ZERO, BigDecimal.valueOf(0));

        final boolean isSuccessful = sender.transferTo(receiver, BigDecimal.valueOf(100));

        assertTrue(isSuccessful);

        assertEquals(BigDecimal.ZERO, sender.getBalance());
        assertEquals(BigDecimal.valueOf(100), receiver.getBalance());
    }

    @Test
    public void shouldReturnFalseAndMoneyNotTransferredWhenSenderHasNotEnoughMoney() {
        final AccountBalance sender = new AccountBalance(BigInteger.ZERO, BigDecimal.valueOf(100));
        final AccountBalance receiver = new AccountBalance(BigInteger.ZERO, BigDecimal.valueOf(0));

        final boolean isSuccessful = sender.transferTo(receiver, BigDecimal.valueOf(101));

        assertFalse(isSuccessful);

        assertEquals(BigDecimal.valueOf(100), sender.getBalance());
        assertEquals(BigDecimal.ZERO, receiver.getBalance());


    }
}
