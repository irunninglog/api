package com.irunninglog.vertx.mock;

import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.data.IGetRoutesResponse;

final class MockGetRoutesResponse implements IGetRoutesResponse<MockRoutes, MockGetRoutesResponse> {

    private ResponseStatus status;
    private MockRoutes body;

    @Override
    public MockGetRoutesResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public MockGetRoutesResponse setBody(MockRoutes body) {
        this.body = body;
        return this;
    }

    @Override
    public MockRoutes getBody() {
        return body;
    }

}
