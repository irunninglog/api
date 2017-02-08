package com.irunninglog.vertx.mock;

import com.irunninglog.api.workout.IGetWorkoutsRequest;

class MockGetWorkoutsRequest implements IGetWorkoutsRequest {

    private int offset;
    private long profileId;
    private String date;

    @Override
    public IGetWorkoutsRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public IGetWorkoutsRequest setProfileId(long profileId) {
        this.profileId = profileId;
        return this;
    }

    @Override
    public long getProfileId() {
        return profileId;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public IGetWorkoutsRequest setDate(String date) {
        this.date = date;
        return this;
    }
}
