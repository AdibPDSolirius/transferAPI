package com.revolut.transfer.api.request;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TransferDTO {

    private BigDecimal amount;
    private BigInteger senderID;
    private BigInteger receiverID;

    public TransferDTO(BigDecimal amount, BigInteger senderID, BigInteger receiverID) {
        this.amount = amount;
        this.senderID = senderID;
        this.receiverID = receiverID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigInteger getSenderID() {
        return senderID;
    }

    public BigInteger getReceiverID() {
        return receiverID;
    }
}
