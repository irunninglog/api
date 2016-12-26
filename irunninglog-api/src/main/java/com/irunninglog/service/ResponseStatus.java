package com.irunninglog.service;

import com.google.common.base.MoreObjects;

public enum ResponseStatus {

    Ok(200, "Ok"),
    Bad(400, "Bad Request"),
    Unauthenticated(401, "Not Authenticated"),
    Unauthorized(403, "Not Authorized"),
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("message", message)
                .toString();
    }

}
