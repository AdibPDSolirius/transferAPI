package com.revolut.transfer.api.validation;

import java.math.BigInteger;

import com.revolut.transfer.api.request.TransferDTO;

public class AccountIDValidator implements TransferValidator {

    public boolean validate(final TransferDTO transferDTO) {
        final boolean notNull = transferDTO.getSenderID() != null && transferDTO.getReceiverID() != null;

        if (notNull) {
            final boolean notNegative = transferDTO.getSenderID().compareTo(BigInteger.ZERO) >= 0
                    && transferDTO.getReceiverID().compareTo(BigInteger.ZERO) >= 0;

            if(notNegative) {
                return !isSame(transferDTO);
            }
        }

        return false;
    }

    private boolean isSame(final TransferDTO transferDTO) {
        return transferDTO.getSenderID().compareTo(transferDTO.getReceiverID()) == 0;
    }
}
