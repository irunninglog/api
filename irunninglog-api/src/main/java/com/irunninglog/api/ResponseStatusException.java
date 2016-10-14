package com.irunninglog.api;

import com.google.common.base.MoreObjects;

public final class ResponseStatusException extends RuntimeException {

    private final ResponseStatus responseStatus;

    public ResponseStatusException(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("responseStatus", responseStatus)
                .toString();
    }

}
