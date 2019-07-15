package com.revolut.transfer.api.response;

public enum ResponseMessage {

    INSUFFICIENT_FUNDS("Insufficient funds"),
    SENDER_ACCOUNT_NOT_IN_DATABASE("Sender account not in data"),
    RECEIVER_ACCOUNT_NOT_IN_DATABASE("Receiver account not in data"),
    TRANSFER_SUCCESSFUL("Transfer successful");

    private String responseMessage;

    ResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }
}
