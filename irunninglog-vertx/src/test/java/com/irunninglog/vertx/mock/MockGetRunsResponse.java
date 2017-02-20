package com.irunninglog.vertx.mock;

import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.data.IGetRunsResponse;

final class MockGetRunsResponse implements IGetRunsResponse<MockRuns, MockGetRunsResponse> {

    private ResponseStatus status;
    private MockRuns body;

    @Override
    public MockGetRunsResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public MockGetRunsResponse setBody(MockRuns body) {
        this.body = body;
        return this;
    }

    @Override
    public MockRuns getBody() {
        return body;
    }

}
