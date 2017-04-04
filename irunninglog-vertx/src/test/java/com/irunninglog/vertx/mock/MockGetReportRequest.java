package com.irunninglog.vertx.mock;

import com.irunninglog.api.report.IGetReportRequest;

final class MockGetReportRequest implements IGetReportRequest {

    private int offset;
    private long profileId;

    @Override
    public IGetReportRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public IGetReportRequest setProfileId(long profileId) {
        this.profileId = profileId;
        return this;
    }

    @Override
    public long getProfileId() {
        return profileId;
    }
}
