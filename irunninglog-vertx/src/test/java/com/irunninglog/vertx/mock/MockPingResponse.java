package com.irunninglog.vertx.mock;

import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ping.IPingResponse;

final class MockPingResponse implements IPingResponse<MockPing, MockPingResponse> {

    private ResponseStatus status;
    private MockPing body;

    @Override
    public MockPingResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public MockPingResponse setBody(MockPing body) {
        this.body = body;
        return this;
    }

    @Override
    public MockPing getBody() {
        return body;
    }

}
