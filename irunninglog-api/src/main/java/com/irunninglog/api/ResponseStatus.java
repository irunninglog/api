package com.irunninglog.api;

import com.google.common.base.MoreObjects;

public enum ResponseStatus {

    OK(200, "Ok"),
    BAD(400, "Bad Request"),
    UNAUTHENTICATED(401, "Not Authenticated"),
    UNAUTHORIZED(403, "Not Authorized"),
    NOT_FOUND(404, "Not Found"),
    ERROR(500, "Unknown Error");

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
