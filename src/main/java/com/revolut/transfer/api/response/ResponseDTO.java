package com.revolut.transfer.api.response;

public class ResponseDTO {
    private String status;
    private String message;

    public ResponseDTO(ResponseStatus responseStatus, ResponseMessage message) {
        this.status = responseStatus.getResponseStatus();
        this.message = message.getResponseMessage();
    }

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }
}
