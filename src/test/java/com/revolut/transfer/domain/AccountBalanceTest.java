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

    private static final BigDecimal AMOUNT_IN_SENDER_ACCOUNT = BigDecimal.TEN;

    @Test
    public void shouldReturnTrueAndMoneyIsTransferredWhenSenderHasEnoughMoney() {
        final AccountBalance sender = new AccountBalance(BigInteger.ZERO, AMOUNT_IN_SENDER_ACCOUNT);
        final AccountBalance receiver = new AccountBalance(BigInteger.ZERO, BigDecimal.ZERO);

        final boolean isSuccessful = sender.transferTo(receiver, AMOUNT_IN_SENDER_ACCOUNT);

        assertTrue(isSuccessful);

        assertEquals(BigDecimal.ZERO, sender.getBalance());
        assertEquals(BigDecimal.TEN, receiver.getBalance());
    }

    @Test
    public void shouldReturnFalseAndMoneyNotTransferredWhenSenderHasNotEnoughMoney() {
        final AccountBalance sender = new AccountBalance(BigInteger.ZERO, AMOUNT_IN_SENDER_ACCOUNT);
        final AccountBalance receiver = new AccountBalance(BigInteger.ZERO, BigDecimal.ZERO);

        final boolean isSuccessful = sender.transferTo(receiver, AMOUNT_IN_SENDER_ACCOUNT.add(BigDecimal.ONE));

        assertFalse(isSuccessful);

        assertEquals(BigDecimal.TEN, sender.getBalance());
        assertEquals(BigDecimal.ZERO, receiver.getBalance());


    }
}
