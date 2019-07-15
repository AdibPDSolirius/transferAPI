package com.revolut.transfer.api;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.revolut.transfer.api.request.TransferDTO;
import com.revolut.transfer.api.response.ResponseParameters;
import com.revolut.transfer.application.ThreadHandler;
import com.revolut.transfer.application.AccountBalanceService;
import spark.Request;
import spark.Response;


public class Handler {

    @Inject
    private AccountBalanceService accountBalanceService;

    @Inject
    private ThreadHandler threadHandler;

    private ResponseParameters responseParameters;

    private Gson gson;

    public Handler() {
        gson = new Gson();

    }

    String processTransferRequest(final Request request, final Response response) {
        final TransferDTO transferDTO = gson.fromJson(request.body(), TransferDTO.class);

        threadHandler.manageThreads(transferDTO.getSenderID(), transferDTO.getReceiverID(),
                () -> responseParameters = accountBalanceService.transfer(transferDTO));

        response.header("Content-Type", "application/json");
        response.status(responseParameters.getHttpStatus());

        return gson.toJson(responseParameters.getResponseDTO());
    }
}