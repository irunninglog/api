package com.irunninglog.vertx.mock;

import com.irunninglog.api.identity.IIdentityRequest;

class MockIdentityRequest implements IIdentityRequest<MockIdentityRequest> {

    private int offset;
    private String username;

    @Override
    public MockIdentityRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public MockIdentityRequest username(String username) {
        this.username = username;
        return this;
    }

}
