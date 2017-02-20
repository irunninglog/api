package com.irunninglog.vertx.mock;

import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.data.IGetShoesResponse;

final class MockGetShoesResponse implements IGetShoesResponse<MockShoes, MockGetShoesResponse> {

    private ResponseStatus status;
    private MockShoes body;

    @Override
    public MockGetShoesResponse setStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public MockGetShoesResponse setBody(MockShoes body) {
        this.body = body;
        return this;
    }

    @Override
    public MockShoes getBody() {
        return body;
    }

}
