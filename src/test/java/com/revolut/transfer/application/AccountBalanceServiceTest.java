package com.revolut.transfer.application;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.revolut.transfer.api.request.TransferDTO;
import com.revolut.transfer.api.response.ResponseMessage;
import com.revolut.transfer.api.response.ResponseParameters;
import com.revolut.transfer.api.response.ResponseStatus;
import com.revolut.transfer.application.AccountBalanceService;
import com.revolut.transfer.data.AccountBalanceRepository;
import com.revolut.transfer.domain.AccountBalance;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountBalanceServiceTest {

    @InjectMocks
    private AccountBalanceService accountBalanceService;

    @Mock
    private AccountBalanceRepository accountBalanceRepository;

    @Mock
    private AccountBalance accountBalanceSender;

    @Mock
    private AccountBalance accountBalanceReceiver;

    private static final BigDecimal AMOUNT_IN_ACCOUNT = BigDecimal.TEN;

    private static final BigInteger SENDER_ID = BigInteger.ZERO;
    private static final BigInteger RECEIVER_ID = BigInteger.ONE;

    private static final TransferDTO TRANSFER_DTO = new TransferDTO(AMOUNT_IN_ACCOUNT, SENDER_ID, RECEIVER_ID);

    @Test
    public void shouldReturnSuccessResponseAndDatabaseUpdatedWhenTransferSucceeded() {
        when(accountBalanceRepository.findAccountBalanceByID(SENDER_ID)).thenReturn(accountBalanceSender);
        when(accountBalanceRepository.findAccountBalanceByID(RECEIVER_ID)).thenReturn(accountBalanceReceiver);
        when(accountBalanceSender.transferTo(accountBalanceReceiver, TRANSFER_DTO.getAmount())).thenReturn(true);

        final ResponseParameters responseParameters = accountBalanceService.transfer(TRANSFER_DTO);

        assertEquals(HttpStatus.OK_200, responseParameters.getHttpStatus());
        assertEquals(ResponseStatus.SUCCESS.getStatus(), responseParameters.getResponseDTO().getStatus());
        assertEquals(ResponseMessage.TRANSFER_SUCCESSFUL.getMessage(), responseParameters.getResponseDTO().getMessage());

        verifyDBCalls(1);
    }

    @Test
    public void shouldReturnSenderAccountNotFoundResponseAndDatabaseNotUpdatedWhenSenderAccountIsNull() {
        when(accountBalanceRepository.findAccountBalanceByID(SENDER_ID)).thenReturn(null);

        final ResponseParameters responseParameters = accountBalanceService.transfer(TRANSFER_DTO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, responseParameters.getHttpStatus());
        assertEquals(ResponseStatus.FAILURE.getStatus(), responseParameters.getResponseDTO().getStatus());
        assertEquals(ResponseMessage.SENDER_ACCOUNT_NOT_FOUND.getMessage(), responseParameters.getResponseDTO().getMessage());

        verifyDBCalls(0);
    }

    @Test
    public void shouldReturnReceiverAccountNotFoundResponseAndDatabaseNotUpdatedWhenReceiverAccountIsNull() {
        when(accountBalanceRepository.findAccountBalanceByID(SENDER_ID)).thenReturn(accountBalanceSender);
        when(accountBalanceRepository.findAccountBalanceByID(RECEIVER_ID)).thenReturn(null);

        final ResponseParameters responseParameters = accountBalanceService.transfer(TRANSFER_DTO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, responseParameters.getHttpStatus());
        assertEquals(ResponseStatus.FAILURE.getStatus(), responseParameters.getResponseDTO().getStatus());
        assertEquals(ResponseMessage.RECEIVER_ACCOUNT_NOT_FOUND.getMessage(), responseParameters.getResponseDTO().getMessage());

        verifyDBCalls(0);
    }

    @Test
    public void shouldReturnInsufficientFundsResponseAndDatabaseNotUpdatedWhenTransferFailed() {
        when(accountBalanceRepository.findAccountBalanceByID(SENDER_ID)).thenReturn(accountBalanceSender);
        when(accountBalanceRepository.findAccountBalanceByID(RECEIVER_ID)).thenReturn(accountBalanceReceiver);
        when(accountBalanceSender.transferTo(accountBalanceReceiver, TRANSFER_DTO.getAmount())).thenReturn(false);

        final ResponseParameters responseParameters = accountBalanceService.transfer(TRANSFER_DTO);

        assertEquals(HttpStatus.CONFLICT_409, responseParameters.getHttpStatus());
        assertEquals(ResponseStatus.FAILURE.getStatus(), responseParameters.getResponseDTO().getStatus());
        assertEquals(ResponseMessage.INSUFFICIENT_FUNDS.getMessage(), responseParameters.getResponseDTO().getMessage());

        verifyDBCalls(0);
    }

    void verifyDBCalls(final int noCalls) {
        verify(accountBalanceRepository, times(noCalls)).saveAccountBalance(accountBalanceSender);
        verify(accountBalanceRepository, times(noCalls)).saveAccountBalance(accountBalanceReceiver);
    }
}
