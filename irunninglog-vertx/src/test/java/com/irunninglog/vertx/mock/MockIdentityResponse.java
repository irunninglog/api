package com.irunninglog.vertx.mock;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.identity.IIdentity;
import com.irunninglog.api.identity.IIdentityResponse;

class MockIdentityResponse implements IIdentityResponse<MockIdentityResponse> {

    private ResponseStatus status;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = MockIdentity.class)
    private IIdentity body;

    @Override
    public MockIdentityResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public MockIdentityResponse setBody(IIdentity body) {
        this.body = body;
        return this;
    }

    @Override
    public IIdentity getBody() {
        return body;
    }

}
