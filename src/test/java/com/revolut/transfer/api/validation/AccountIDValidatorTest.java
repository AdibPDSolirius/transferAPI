package com.revolut.transfer.api.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.revolut.transfer.api.request.TransferDTO;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

@RunWith(JUnit4ClassRunner.class)
public class AccountIDValidatorTest {

    private final AccountIDValidator accountIDValidator = new AccountIDValidator();

    @Test
    public void shouldReturnTrueWhenSenderAndReceiverIDNotNullAndNotNegative() {
        TransferDTO transferDTO = new TransferDTO(BigDecimal.TEN, BigInteger.ZERO, BigInteger.ONE);
        boolean isPass = accountIDValidator.validate(transferDTO);
        assertTrue(isPass);
    }

    @Test
    public void shouldReturnFalseWhenSenderAndReceiverIDNotNullAndSenderIDNegative() {
        TransferDTO transferDTO = new TransferDTO(BigDecimal.TEN, BigInteger.valueOf(-1), BigInteger.ONE);
        boolean isPass = accountIDValidator.validate(transferDTO);
        assertFalse(isPass);
    }

    @Test
    public void shouldReturnFalseWhenSenderAndReceiverIDNotNullAndReceiverIDNegative() {
        TransferDTO transferDTO = new TransferDTO(BigDecimal.TEN, BigInteger.ZERO, BigInteger.valueOf(-2));
        boolean isPass = accountIDValidator.validate(transferDTO);
        assertFalse(isPass);
    }

    @Test
    public void shouldReturnFalseWhenSenderIDNull() {
        TransferDTO transferDTO = new TransferDTO(BigDecimal.TEN, null, BigInteger.ONE);
        boolean isPass = accountIDValidator.validate(transferDTO);
        assertFalse(isPass);
    }

    @Test
    public void shouldReturnFalseWhenReceiverIDNull() {
        TransferDTO transferDTO = new TransferDTO(BigDecimal.TEN, BigInteger.ZERO,null);
        boolean isPass = accountIDValidator.validate(transferDTO);
        assertFalse(isPass);
    }

    @Test
    public void shouldReturnTrueWhenSenderIsNotReceiver() {
        TransferDTO transferDTO = new TransferDTO(BigDecimal.TEN, BigInteger.ZERO, BigInteger.ONE);
        boolean isPass = accountIDValidator.validate(transferDTO);
        assertTrue(isPass);
    }

    @Test
    public void shouldReturnFalseWhenSenderIsReceiver() {
        TransferDTO transferDTO = new TransferDTO(BigDecimal.TEN, BigInteger.ZERO, BigInteger.ZERO);
        boolean isPass = accountIDValidator.validate(transferDTO);
        assertFalse(isPass);
    }
}
