package com.masalaboratory.vegetable.controller;

public class APIError<D> {

    private final String message;
    private final D data;

    public String getMessage() {
        return message;
    }

    public D getData() {
        return data;
    }

    public APIError(String message, D data) {
        this.message = message;
        this.data = data;
    }

    public APIError(String message) {
        this(message, null);
    }

}