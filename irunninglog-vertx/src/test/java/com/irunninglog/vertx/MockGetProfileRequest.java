package com.irunninglog.vertx;

import com.irunninglog.api.profile.IGetProfileRequest;

public class MockGetProfileRequest implements IGetProfileRequest {

    private int offset;
    private long profileId;

    @Override
    public IGetProfileRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public IGetProfileRequest setProfileId(long profileId) {
        this.profileId = profileId;
        return this;
    }

    @Override
    public long getProfileId() {
        return profileId;
    }

}
