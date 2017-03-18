package com.irunninglog.vertx.mock;

import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.security.IAuthnResponse;

class MockAuthnResponse implements IAuthnResponse<MockUser, MockAuthnResponse> {

    private ResponseStatus status;
    private MockUser body;
    private String token;

    @Override
    public MockAuthnResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public MockAuthnResponse setBody(MockUser body) {
        this.body = body;
        return this;
    }

    @Override
    public MockUser getBody() {
        return body;
    }

    public String getToken() {
        return token;
    }

    @Override
    public MockAuthnResponse setToken(String token) {
        this.token = token;
        return this;
    }

}
