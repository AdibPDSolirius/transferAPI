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

    private final SenderReceiverValidator senderReceiverValidator = new SenderReceiverValidator();

    @Test
    public void shouldReturnTrueWhenSenderIsNotReceiver() {
        TransferDTO transferDTO = new TransferDTO(BigDecimal.TEN, BigInteger.ZERO, BigInteger.ONE);
        boolean isPass = senderReceiverValidator.validate(transferDTO);
        assertTrue(isPass);
    }

    @Test
    public void shouldReturnFalseWhenSenderIsReceiver() {
        TransferDTO transferDTO = new TransferDTO(BigDecimal.TEN, BigInteger.ZERO, BigInteger.ZERO);
        boolean isPass = senderReceiverValidator.validate(transferDTO);
        assertFalse(isPass);
    }

}
