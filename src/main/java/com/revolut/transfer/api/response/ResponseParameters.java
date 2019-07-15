package com.revolut.transfer.api.response;

public class ResponseParameters {

    private int httpStatus;
    private ResponseDTO responsePOJO;

    private String status;
    private String message;

    public ResponseParameters(int httpStatus, ResponseDTO responsePOJO) {
        this.httpStatus = httpStatus;
        this.responsePOJO = responsePOJO;
    }

    public int getHttpStatus() {
        return this.httpStatus;
    }

    public ResponseDTO getResponsePOJO() {
        return this.responsePOJO;
    }

}
