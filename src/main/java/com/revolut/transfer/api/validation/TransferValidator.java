package com.revolut.transfer.api.validation;

import com.revolut.transfer.api.request.TransferDTO;

public interface TransferValidator {

    boolean validate(final TransferDTO transferDTO);
}
