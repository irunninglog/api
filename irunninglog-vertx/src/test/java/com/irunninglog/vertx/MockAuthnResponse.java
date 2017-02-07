package com.irunninglog.vertx;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.security.IAuthnResponse;
import com.irunninglog.api.security.IUser;

public class MockAuthnResponse implements IAuthnResponse {

    private ResponseStatus status;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = MockUser.class)
    private IUser body;

    @Override
    public IAuthnResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public IAuthnResponse setBody(IUser body) {
        this.body = body;
        return this;
    }

    @Override
    public IUser getBody() {
        return body;
    }

}
