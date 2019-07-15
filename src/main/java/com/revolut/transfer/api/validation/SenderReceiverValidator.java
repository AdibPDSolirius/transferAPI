package com.revolut.transfer.api.validation;

import com.revolut.transfer.api.request.TransferDTO;

public class SenderReceiverValidator implements TransferValidator {

    public boolean validate(final TransferDTO transferDTO) {
        return transferDTO.getSenderID().compareTo(transferDTO.getReceiverID()) != 0;
    }
}
