package com.revolut.transfer.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revolut.transfer.api.request.TransferDTO;
import com.revolut.transfer.api.response.ResponseMessage;
import com.revolut.transfer.api.response.ResponseDTO;
import com.revolut.transfer.api.response.ResponseParameters;
import com.revolut.transfer.api.response.ResponseStatus;
import com.revolut.transfer.application.AccountBalanceService;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @InjectMocks
    private Controller controller;

    @Mock
    private AccountBalanceService accountBalanceService;

    @Spy
    private ThreadHandlerTestImpl threadHandler;

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Test
    public void shouldReturnSameResponseThatServiceReturns() {
        final ResponseParameters mockResponseParameters = getMockResponseParameters();
        when(accountBalanceService.transfer(any(TransferDTO.class))).thenReturn(mockResponseParameters);
        when(request.body()).thenReturn(getMockTransferPayload());

        final String responseJSON = controller.processTransferRequest(request, response);

        assertEquals(new Gson().toJson(mockResponseParameters.getResponseDTO()), responseJSON);


    }

    private ResponseParameters getMockResponseParameters () {
        return new ResponseParameters(HttpStatus.OK_200,
                new ResponseDTO(ResponseStatus.SUCCESS, ResponseMessage.TRANSFER_SUCCESSFUL));
    }

    private String getMockTransferPayload() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("senderID", "0");
        jsonObject.addProperty("receiverID", "1");
        jsonObject.addProperty("amount", "2");

        return  jsonObject.toString();
    }
}
