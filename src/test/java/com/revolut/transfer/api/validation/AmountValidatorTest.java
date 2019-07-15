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
public class AmountValidatorTest {

    private final AmountValidator amountValidator = new AmountValidator();

    @Test
    public void shouldReturnTrueWhenAmountGreaterThanZero() {
        TransferDTO transferDTO = new TransferDTO(BigDecimal.TEN, BigInteger.ZERO, BigInteger.ONE);
        boolean isPass = amountValidator.validate(transferDTO);
        assertTrue(isPass);
    }

    @Test
    public void shouldReturnFalseWhenAmountEqualsZero() {
        TransferDTO transferDTO = new TransferDTO(BigDecimal.ZERO, BigInteger.ZERO, BigInteger.ONE);
        boolean isPass = amountValidator.validate(transferDTO);
        assertFalse(isPass);
    }

    @Test
    public void shouldReturnFalseWhenAmountLessThanZero() {
        TransferDTO transferDTO = new TransferDTO(BigDecimal.valueOf(-50), BigInteger.ZERO, BigInteger.ONE);
        boolean isPass = amountValidator.validate(transferDTO);
        assertFalse(isPass);
    }
}
