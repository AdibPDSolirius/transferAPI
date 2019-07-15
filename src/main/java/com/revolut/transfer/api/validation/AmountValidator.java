package com.revolut.transfer.api.validation;

import java.math.BigDecimal;

import com.revolut.transfer.api.request.TransferDTO;

public class AmountValidator implements TransferValidator {

    public boolean validate(final TransferDTO transferDTO) {
        final boolean notNull = transferDTO.getAmount() != null;

        if (notNull) {
            return transferDTO.getAmount().compareTo(BigDecimal.ZERO) > 0;
        }

        return false;
    }
}
