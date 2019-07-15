package com.revolut.transfer.infrastructure;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.revolut.transfer.api.validation.AccountIDValidator;
import com.revolut.transfer.api.validation.AmountValidator;
import com.revolut.transfer.api.validation.TransferValidator;

public class FilterModule extends AbstractModule {

    @Override
    public void configure() {
        Multibinder<TransferValidator> transferValidatorMultibinder =
                Multibinder.newSetBinder(binder(), TransferValidator.class);

        transferValidatorMultibinder.addBinding().to(AccountIDValidator.class);
        transferValidatorMultibinder.addBinding().to(AmountValidator.class);
    }
}
