package com.revolut.transfer.api.validation;

import java.math.BigInteger;

import com.revolut.transfer.api.request.TransferDTO;

public class AccountIDValidator implements TransferValidator {

    public boolean validate(final TransferDTO transferDTO) {
        final boolean notNull = transferDTO.getSenderID() != null && transferDTO.getReceiverID() != null;
        if (notNull) {
            return transferDTO.getSenderID().compareTo(BigInteger.ZERO) >= 0
                    && transferDTO.getReceiverID().compareTo(BigInteger.ZERO) >= 0;
        }
        return false;
    }
}
