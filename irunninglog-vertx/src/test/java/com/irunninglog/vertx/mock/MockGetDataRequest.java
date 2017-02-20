package com.irunninglog.vertx.mock;

import com.irunninglog.api.data.IGetDataRequest;

final class MockGetDataRequest implements IGetDataRequest {

    private int offset;
    private long profileId;

    @Override
    public IGetDataRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public IGetDataRequest setProfileId(long profileId) {
        this.profileId = profileId;
        return this;
    }

    @Override
    public long getProfileId() {
        return profileId;
    }

}
