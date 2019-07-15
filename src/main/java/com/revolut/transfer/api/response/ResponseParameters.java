package com.revolut.transfer.api.response;

public class ResponseParameters {

    private int httpStatus;
    private ResponseDTO responseDTO;

    public ResponseParameters(int httpStatus, ResponseDTO responseDTO) {
        this.httpStatus = httpStatus;
        this.responseDTO = responseDTO;
    }

    public int getHttpStatus() {
        return this.httpStatus;
    }

    public ResponseDTO getResponseDTO() {
        return this.responseDTO;
    }

}
