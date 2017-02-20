package com.irunninglog.vertx.mock;

import com.irunninglog.api.security.ILoginRequest;

final class MockLoginRequest implements ILoginRequest<MockLoginRequest, MockUser> {

    private int offset;
    private MockUser user;

    @Override
    public MockLoginRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public ILoginRequest setUser(MockUser user) {
        this.user = user;
        return this;
    }

    @Override
    public MockUser getUser() {
        return user;
    }

}
