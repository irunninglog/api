package com.irunninglog.service;

import com.google.common.base.MoreObjects;

public abstract class AbstractResponse<T, S extends AbstractResponse> {

    @SuppressWarnings("unchecked")
    private final S myself = (S) this;

    private ResponseStatus status;
    private T body;

    public final ResponseStatus getStatus() {
        return status;
    }

    public final S setStatus(ResponseStatus status) {
        this.status = status;
        return myself;
    }

    public final T getBody() {
        return body;
    }

    public final S setBody(T body) {
        this.body = body;
        return myself;
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", status)
                .add("body", body)
                .toString();
    }

}
