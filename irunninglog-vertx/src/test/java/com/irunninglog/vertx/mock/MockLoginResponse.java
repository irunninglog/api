package com.irunninglog.vertx.mock;

import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.security.ILoginResponse;

final class MockLoginResponse implements ILoginResponse<MockLogin, MockLoginResponse> {

    private ResponseStatus status;
    private MockLogin body;

    @Override
    public MockLoginResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public MockLoginResponse setBody(MockLogin body) {
        this.body = body;
        return this;
    }

    @Override
    public MockLogin getBody() {
        return body;
    }

}
