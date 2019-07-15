package com.revolut.transfer.api.response;

public enum ResponseStatus {

    SUCCESS("Success"),
    FAILURE("Failure");

    private String responseStatus;

    ResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseStatus() {
        return this.responseStatus;
    }
}
