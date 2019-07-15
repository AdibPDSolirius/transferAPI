package com.revolut.transfer;

import static spark.Spark.port;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.transfer.api.Router;
import com.revolut.transfer.data.AccountBalanceRepository;
import com.revolut.transfer.domain.AccountBalance;
import com.revolut.transfer.infrastructure.HandlerModule;


public class Application {

    public static final int PORT = 8080;

    public static void main(String[] args) {
        port(PORT);

        final Injector injector = Guice.createInjector(new HandlerModule());

        initialiseDatabase(injector.getInstance(AccountBalanceRepository.class));

        final Router router = injector.getInstance(Router.class);
        router.handleRequests();

    }

    private static void initialiseDatabase(final AccountBalanceRepository accountBalanceRepository) {
        accountBalanceRepository.saveAccountBalance(new AccountBalance(BigInteger.ZERO, BigDecimal.valueOf(100)));
        accountBalanceRepository.saveAccountBalance(new AccountBalance(BigInteger.ONE, BigDecimal.valueOf(100)));
    }
}