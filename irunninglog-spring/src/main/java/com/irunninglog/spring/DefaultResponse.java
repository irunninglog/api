package com.irunninglog.spring;

import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;

final class DefaultResponse implements IResponse {

    private ResponseStatus status;
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

}
