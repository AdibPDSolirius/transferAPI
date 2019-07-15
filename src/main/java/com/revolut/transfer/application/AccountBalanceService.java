package com.revolut.transfer.application;

import com.google.inject.Inject;
import com.revolut.transfer.api.request.TransferDTO;
import com.revolut.transfer.api.response.ResponseDTO;
import com.revolut.transfer.api.response.ResponseMessage;
import com.revolut.transfer.api.response.ResponseParameters;
import com.revolut.transfer.api.response.ResponseStatus;
import com.revolut.transfer.data.AccountBalanceRepository;
import com.revolut.transfer.domain.AccountBalance;
import org.eclipse.jetty.http.HttpStatus;


public class AccountBalanceService {

    @Inject
    private AccountBalanceRepository accountBalanceRepository;

    public ResponseParameters transfer(final TransferDTO transferDTO) {

        final AccountBalance sender = accountBalanceRepository.findAccountBalanceByID(transferDTO.getSenderID());
        if(sender == null) {
            return new ResponseParameters(HttpStatus.UNPROCESSABLE_ENTITY_422,
                    new ResponseDTO(ResponseStatus.FAILURE, ResponseMessage.SENDER_ACCOUNT_NOT_FOUND));
        }

        final AccountBalance receiver = accountBalanceRepository.findAccountBalanceByID(transferDTO.getReceiverID());
        if(receiver == null) {
            return new ResponseParameters(HttpStatus.UNPROCESSABLE_ENTITY_422,
                    new ResponseDTO(ResponseStatus.FAILURE, ResponseMessage.RECEIVER_ACCOUNT_NOT_FOUND));
        }

        final boolean isSuccess = sender.transferTo(receiver, transferDTO.getAmount());

        if(isSuccess) {
            accountBalanceRepository.saveAccountBalance(sender);
            accountBalanceRepository.saveAccountBalance(receiver);

            return new ResponseParameters(HttpStatus.OK_200,
                    new ResponseDTO(ResponseStatus.SUCCESS, ResponseMessage.TRANSFER_SUCCESSFUL));
        }

        return new ResponseParameters(HttpStatus.CONFLICT_409,
                new ResponseDTO(ResponseStatus.FAILURE, ResponseMessage.INSUFFICIENT_FUNDS));

    }

}
