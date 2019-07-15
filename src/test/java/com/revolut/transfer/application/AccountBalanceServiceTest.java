package com.revolut.transfer.application;

import static org.junit.Assert.assertEquals;
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
    private AccountBalanceService transferService;

    @Mock
    private AccountBalanceRepository transferRepository;

    @Mock
    private AccountBalance accountBalance;

    private static final BigDecimal AMOUNT_IN_ACCOUNT = BigDecimal.TEN;

    private static final BigInteger SENDER_ID = BigInteger.ZERO;
    private static final BigInteger RECEIVER_ID = BigInteger.ONE;

    private static final TransferDTO TRANSFER_POJO = new TransferDTO(AMOUNT_IN_ACCOUNT, SENDER_ID, RECEIVER_ID);

    @Test
    public void shouldReturnSenderAccountNotInDatabaseResponseWhenSenderAccountIsNull() {
        when(transferRepository.findAccountBalanceByID(SENDER_ID)).thenReturn(null);

        final ResponseParameters responseParameters = transferService.transfer(TRANSFER_POJO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, responseParameters.getHttpStatus());
        assertEquals(ResponseStatus.FAILURE.getResponseStatus(), responseParameters.getResponsePOJO().getStatus());
        assertEquals(ResponseMessage.SENDER_ACCOUNT_NOT_IN_DATABASE.getResponseMessage(), responseParameters.getResponsePOJO().getMessage());

    }

    @Test
    public void shouldReturnReceiverAccountNotInDatabaseResponseWhenReceiverAccountIsNull() {
        when(transferRepository.findAccountBalanceByID(RECEIVER_ID)).thenReturn(null);
        when(transferRepository.findAccountBalanceByID(SENDER_ID)).thenReturn(accountBalance);

        final ResponseParameters responseParameters = transferService.transfer(TRANSFER_POJO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, responseParameters.getHttpStatus());
        assertEquals(ResponseStatus.FAILURE.getResponseStatus(), responseParameters.getResponsePOJO().getStatus());
        assertEquals(ResponseMessage.RECEIVER_ACCOUNT_NOT_IN_DATABASE.getResponseMessage(), responseParameters.getResponsePOJO().getMessage());

    }

    @Test
    public void shouldReturnInsufficientFundsResponseWhenTransferFailed() {
        when(transferRepository.findAccountBalanceByID(RECEIVER_ID)).thenReturn(accountBalance);
        when(transferRepository.findAccountBalanceByID(SENDER_ID)).thenReturn(accountBalance);
        when(accountBalance.transferTo(accountBalance, TRANSFER_POJO.getAmount())).thenReturn(false);

        final ResponseParameters responseParameters = transferService.transfer(TRANSFER_POJO);

        assertEquals(HttpStatus.CONFLICT_409, responseParameters.getHttpStatus());
        assertEquals(ResponseStatus.FAILURE.getResponseStatus(), responseParameters.getResponsePOJO().getStatus());
        assertEquals(ResponseMessage.INSUFFICIENT_FUNDS.getResponseMessage(), responseParameters.getResponsePOJO().getMessage());

    }

    @Test
    public void shouldReturnSuccessResponseWhenTransferSucceeded() {
        when(transferRepository.findAccountBalanceByID(RECEIVER_ID)).thenReturn(accountBalance);
        when(transferRepository.findAccountBalanceByID(SENDER_ID)).thenReturn(accountBalance);
        when(accountBalance.transferTo(accountBalance, TRANSFER_POJO.getAmount())).thenReturn(true);

        final ResponseParameters responseParameters = transferService.transfer(TRANSFER_POJO);

        assertEquals(HttpStatus.OK_200, responseParameters.getHttpStatus());
        assertEquals(ResponseStatus.SUCCESS.getResponseStatus(), responseParameters.getResponsePOJO().getStatus());
        assertEquals(ResponseMessage.TRANSFER_SUCCESSFUL.getResponseMessage(), responseParameters.getResponsePOJO().getMessage());

    }
}
