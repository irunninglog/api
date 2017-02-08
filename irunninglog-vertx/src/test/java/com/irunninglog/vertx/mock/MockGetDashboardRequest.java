package com.irunninglog.vertx.mock;

import com.irunninglog.api.dashboard.IGetDashboardRequest;

class MockGetDashboardRequest implements IGetDashboardRequest {

    private int offset;
    private long profileId;

    @Override
    public IGetDashboardRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public IGetDashboardRequest setProfileId(long profileId) {
        this.profileId = profileId;
        return this;
    }

    @Override
    public long getProfileId() {
        return profileId;
    }

}
