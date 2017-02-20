package com.irunninglog.vertx.mock;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.dashboard.IGetDashboardResponse;

class MockGetDashboardResponse implements IGetDashboardResponse<MockDashboardInfo, MockGetDashboardResponse> {

    private ResponseStatus status;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = MockDashboardInfo.class)
    private MockDashboardInfo body;

    @Override
    public MockGetDashboardResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public MockGetDashboardResponse setBody(MockDashboardInfo body) {
        this.body = body;
        return this;
    }

    @Override
    public MockDashboardInfo getBody() {
        return body;
    }

}
