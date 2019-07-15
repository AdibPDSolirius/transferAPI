package com.revolut.transfer.api.response;

public enum ResponseMessage {

    INSUFFICIENT_FUNDS("Insufficient funds"),
    SENDER_ACCOUNT_NOT_IN_DATABASE("Sender account not found"),
    RECEIVER_ACCOUNT_NOT_IN_DATABASE("Receiver account not found"),
    TRANSFER_SUCCESSFUL("Transfer successful");

    private String responseMessage;

    ResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }
}
