package com.revolut.transfer.api;

import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.stream.Stream;

import com.revolut.transfer.api.validation.AmountValidator;
import com.revolut.transfer.api.validation.TransferValidator;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import spark.HaltException;
import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class FilterTest {

    @InjectMocks
    private Filter filter;

    @Mock
    private Set<TransferValidator> transferValidatorSet;

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setup() {
        TransferValidator transferValidator = new AmountValidator();
        when(transferValidatorSet.stream()).thenReturn(Stream.of(transferValidator));

    }


    @Test
    public void shouldNotThrowExceptionWhenValidPayload() {
        when(request.body()).thenReturn(getValidMockTransferPayload());

        filter.validateRequest(request, response);
    }

    @Test(expected = HaltException.class)
    public void shouldReturnHaltExceptionWhenInvalidPayloadDataType() {
        filter.validateRequest(request, response);
    }

    @Test(expected = HaltException.class)
    public void shouldReturnHaltExceptionWhenInvalidPayloadParameters() {
        when(request.body()).thenReturn(getMockTransferPayloadWithNegativeAmount());

        filter.validateRequest(request, response);
    }

    private String getValidMockTransferPayload() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("senderID", "1");
        jsonObject.addProperty("receiverID", "2");
        jsonObject.addProperty("amount", "10");
        return  jsonObject.toString();
    }

    private String getMockTransferPayloadWithNegativeAmount() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("senderID", "1");
        jsonObject.addProperty("receiverID", "2");
        jsonObject.addProperty("amount", "-10");
        return  jsonObject.toString();
    }

}
