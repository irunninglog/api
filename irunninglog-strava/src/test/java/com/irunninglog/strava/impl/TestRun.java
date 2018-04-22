package com.irunninglog.strava.impl;

import com.irunninglog.api.runs.IRun;

final class TestRun implements IRun {

    private int id;
    private String name;
    private String startTime;
    private String distance;
    private int duration;
    private String shoes;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public IRun setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IRun setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public IRun setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    @Override
    public String getDistance() {
        return distance;
    }

    @Override
    public IRun setDistance(String distance) {
        this.distance = distance;
        return this;
    }

    public String getShoes() {
        return shoes;
    }

    @Override
    public IRun setShoes(String shoes) {
        this.shoes = shoes;
        return this;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public IRun setDuration(int duration) {
        this.duration = duration;
        return this;
    }
}
