package com.irunninglog.vertx.mock;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.report.IGetMultiSetResponse;
import com.irunninglog.api.report.IMultiSet;

final class MockGetMultiSetResponse implements IGetMultiSetResponse<MockGetMultiSetResponse> {

    private ResponseStatus status;
    private IMultiSet body;

    @Override
    public MockGetMultiSetResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public MockGetMultiSetResponse setBody(IMultiSet body) {
        this.body = body;
        return this;
    }

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = MockMultiSet.class)
    public IMultiSet getBody() {
        return body;
    }

}
