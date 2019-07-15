package com.revolut.transfer.api;

import static spark.Spark.halt;

import java.util.Set;

import com.google.inject.Inject;
import com.revolut.transfer.api.request.TransferDTO;
import com.revolut.transfer.api.validation.TransferValidator;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

public class Filter {

    @Inject
    private Set<TransferValidator> transferValidators;

    private Gson gson;

    public Filter() {
        gson = new Gson();

    }

    void validateRequest(final Request request, final Response response) {
        final TransferDTO transferPOJO = convertToTransferPOJO(request.body());

        if (transferPOJO == null || !isPassedValidation(transferPOJO)) {
            halt(HttpStatus.BAD_REQUEST_400);
        }
    }

    private TransferDTO convertToTransferPOJO(final String tranferJSON) {
        try {
            return gson.fromJson(tranferJSON, TransferDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isPassedValidation(final TransferDTO transferPOJO) {
        return transferValidators.stream().allMatch(transferValidator -> transferValidator.validate(transferPOJO));
    }

}
