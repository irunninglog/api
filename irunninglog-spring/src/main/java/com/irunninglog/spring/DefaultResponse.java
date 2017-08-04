package com.irunninglog.spring;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.MoreObjects;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;

final class DefaultResponse implements IResponse {

    private ResponseStatus status;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    private Object body;

    @Override
    public IResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public IResponse setBody(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", status)
                .add("body", body)
                .toString();
    }

}
