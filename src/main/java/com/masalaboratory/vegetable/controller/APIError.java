package com.masalaboratory.vegetable.controller;

public class APIError<D> {

    public static final int NOT_FOUND_ERROR = 1001;
    public static final int VALIDATION_ERROR = 1002;
    public static final int INTERNAL_SERVER_ERROR = 1003;

    private final String message;
    private final int errorCode;
    private final D data;

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public D getData() {
        return data;
    }

    public APIError(String message, int errorCode, D data) {
        this.message = message;
        this.errorCode = errorCode;
        this.data = data;
    }

    public APIError(String message, int errorCode) {
        this(message, errorCode, null);
    }

}