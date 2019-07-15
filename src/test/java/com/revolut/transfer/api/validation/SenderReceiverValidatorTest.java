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
public class SenderReceiverValidatorTest {

    @Test
    public void shouldReturnTrueWhenSenderIsNotReceiver() {
        TransferDTO transferPOJO = new TransferDTO(BigDecimal.TEN, BigInteger.ZERO, BigInteger.ONE);
        boolean isPass = new SenderReceiverValidator().validate(transferPOJO);
        assertTrue(isPass);
    }

    @Test
    public void shouldReturnFalseWhenSenderIsReceiver() {
        TransferDTO transferPOJO = new TransferDTO(BigDecimal.ZERO, BigInteger.ZERO, BigInteger.ZERO);
        boolean isPass = new SenderReceiverValidator().validate(transferPOJO);
        assertFalse(isPass);
    }

}
