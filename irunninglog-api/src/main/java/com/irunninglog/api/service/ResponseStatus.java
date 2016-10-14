package com.irunninglog.api.service;

public enum ResponseStatus {

    Ok(200, "Ok"),
    Bad(400, "Bad Request"),
    NotFound(404, "Not Found"),
    Error(500, "Unknown Error");

    private final int code;
    private final String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
