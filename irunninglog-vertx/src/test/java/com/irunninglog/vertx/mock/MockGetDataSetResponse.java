package com.irunninglog.vertx.mock;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.report.IDataSet;
import com.irunninglog.api.report.IGetDataSetResponse;

final class MockGetDataSetResponse implements IGetDataSetResponse<MockGetDataSetResponse> {

    private ResponseStatus status;
    private IDataSet body;

    @Override
    public MockGetDataSetResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public MockGetDataSetResponse setBody(IDataSet body) {
        this.body = body;
        return this;
    }

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = MockDataSet.class)
    public IDataSet getBody() {
        return body;
    }

}
