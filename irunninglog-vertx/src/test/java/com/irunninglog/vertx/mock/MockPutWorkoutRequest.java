package com.irunninglog.vertx.mock;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.workout.IPutWorkoutRequest;
import com.irunninglog.api.workout.IWorkoutEntry;

final class MockPutWorkoutRequest implements IPutWorkoutRequest {

    private int offset;
    private long profileId;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = MockWorkoutEntry.class)
    private MockWorkoutEntry workout;

    @Override
    public IPutWorkoutRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public IPutWorkoutRequest setProfileId(long profileId) {
        this.profileId = profileId;
        return this;
    }

    @Override
    public long getProfileId() {
        return profileId;
    }

    @Override
    public IPutWorkoutRequest setWorkout(IWorkoutEntry workout) {
        this.workout = (MockWorkoutEntry) workout;
        return this;
    }

    @Override
    public MockWorkoutEntry getWorkout() {
        return workout;
    }

}
