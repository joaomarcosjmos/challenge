package com.xpand.challenge.exception;

public class RestExceptionResponse {

    private String error;

    public RestExceptionResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
