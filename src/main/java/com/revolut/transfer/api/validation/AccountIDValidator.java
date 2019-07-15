package com.revolut.transfer.api.validation;

import java.math.BigInteger;

import com.revolut.transfer.api.request.TransferDTO;

public class AccountIDValidator implements TransferValidator {

    public boolean validate(final TransferDTO transferPOJO) {
        final boolean notNull = transferPOJO.getSenderID() != null && transferPOJO.getReceiverID() != null;
        if (notNull) {
            return transferPOJO.getSenderID().compareTo(BigInteger.ZERO) >= 0
                    && transferPOJO.getReceiverID().compareTo(BigInteger.ZERO) >= 0;
        }
        return false;
    }
}
