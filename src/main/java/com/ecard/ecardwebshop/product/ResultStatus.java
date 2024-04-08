package com.ecard.ecardwebshop.product;

public class ResultStatus {

    private ResultStatusEnum status;
    private String message;

    public ResultStatus(ResultStatusEnum status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ResultStatusEnum status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
