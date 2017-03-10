package com.irunninglog.vertx.mock;

import com.irunninglog.api.workout.IWorkoutEntry;

final class MockWorkoutEntry implements IWorkoutEntry {

    private long id;
    private long duration;
    private String distance;
    private String date;
    private long routeId;
    private long runId;
    private long shoeId;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public IWorkoutEntry setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public String getDistance() {
        return distance;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public IWorkoutEntry setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public IWorkoutEntry setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public IWorkoutEntry setDistance(String distance) {
        this.distance = distance;
        return this;
    }

    @Override
    public long getRouteId() {
        return routeId;
    }

    @Override
    public long getShoeId() {
        return shoeId;
    }

    @Override
    public long getRunId() {
        return runId;
    }

    @Override
    public IWorkoutEntry setRouteId(long id) {
        this.routeId = id;
        return this;
    }

    @Override
    public IWorkoutEntry setRunId(long id) {
        this.runId = id;
        return this;
    }

    @Override
    public IWorkoutEntry setShoeId(long id) {
        this.shoeId = id;
        return this;
    }

}
