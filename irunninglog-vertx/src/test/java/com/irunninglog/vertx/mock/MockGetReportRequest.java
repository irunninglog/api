package com.irunninglog.vertx.mock;

import com.irunninglog.api.report.IGetReportRequest;

final class MockGetReportRequest implements IGetReportRequest {
    @Override
    public IGetReportRequest setOffset(int offset) {
        return null;
    }

    @Override
    public int getOffset() {
        return 0;
    }

    @Override
    public IGetReportRequest setProfileId(long profileId) {
        return null;
    }

    @Override
    public long getProfileId() {
        return 0;
    }
}
