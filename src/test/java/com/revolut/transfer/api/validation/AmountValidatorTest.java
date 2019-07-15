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

    @Test
    public void shouldReturnTrueWhenAmountGreaterThanZero() {
        TransferDTO transferPOJO = new TransferDTO(BigDecimal.TEN, BigInteger.ZERO, BigInteger.ONE);
        boolean isPass = new AmountValidator().validate(transferPOJO);
        assertTrue(isPass);
    }

    @Test
    public void shouldReturnFalseWhenAmountEqualsZero() {
        TransferDTO transferPOJO = new TransferDTO(BigDecimal.ZERO, BigInteger.ZERO, BigInteger.ONE);
        boolean isPass = new AmountValidator().validate(transferPOJO);
        assertFalse(isPass);
    }

    @Test
    public void shouldReturnFalseWhenAmountLessThanZero() {
        TransferDTO transferPOJO = new TransferDTO(BigDecimal.valueOf(-50), BigInteger.ZERO, BigInteger.ONE);
        boolean isPass = new AmountValidator().validate(transferPOJO);
        assertFalse(isPass);
    }
}
