package com.irunninglog.vertx.mock;

import com.irunninglog.api.security.IForbiddenRequest;

final class MockForbiddenRequest implements IForbiddenRequest<MockForbiddenRequest> {

    private int offset;

    @Override
    public MockForbiddenRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

}
