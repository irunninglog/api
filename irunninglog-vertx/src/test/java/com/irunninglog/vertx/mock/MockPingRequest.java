package com.irunninglog.vertx.mock;

import com.irunninglog.api.ping.IPingRequest;

final class MockPingRequest implements IPingRequest<MockPingRequest> {

    private int offset;

    @Override
    public MockPingRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

}
