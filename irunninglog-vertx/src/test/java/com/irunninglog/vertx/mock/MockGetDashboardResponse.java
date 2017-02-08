package com.irunninglog.vertx.mock;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.dashboard.IDashboardInfo;
import com.irunninglog.api.dashboard.IGetDashboardResponse;

class MockGetDashboardResponse implements IGetDashboardResponse {

    private ResponseStatus status;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = MockDashboardInfo.class)
    private IDashboardInfo body;

    @Override
    public IGetDashboardResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public IGetDashboardResponse setBody(IDashboardInfo body) {
        this.body = body;
        return this;
    }

    @Override
    public IDashboardInfo getBody() {
        return body;
    }

}
