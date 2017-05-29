package com.irunninglog.vertx.mock;

import com.irunninglog.api.profile.IGetProfileRequest;

public class MockGetProfileRequest implements IGetProfileRequest {

    private int offset;
    private long profileId;
    private String token;

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

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public IGetProfileRequest setToken(String token) {
        this.token = token;
        return this;
    }

}
