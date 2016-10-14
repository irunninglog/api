package com.irunninglog.api.service;

import com.google.common.base.MoreObjects;

public abstract class AbstractResponse<T, S extends AbstractResponse> {

    @SuppressWarnings("unchecked")
    private final S myself = (S) this;

    private ResponseStatus status;
    private T body;

    public ResponseStatus getStatus() {
        return status;
    }

    public S setStatus(ResponseStatus status) {
        this.status = status;
        return myself;
    }

    public T getBody() {
        return body;
    }

    public S setBody(T body) {
        this.body = body;
        return myself;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", status)
                .add("body", body)
                .toString();
    }

}
