package com.revolut.transfer.api.validation;

import com.revolut.transfer.api.request.TransferDTO;

public class SenderReceiverValidator implements TransferValidator {

    public boolean validate(final TransferDTO transferPOJO) {
        return transferPOJO.getSenderID().compareTo(transferPOJO.getReceiverID()) != 0;
    }
}
