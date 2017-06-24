package com.irunninglog.strava.impl;

import com.irunninglog.strava.IStravaRun;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

final class StravaRunImpl implements IStravaRun {

    private long id;
    private ZonedDateTime startTime;
    private LocalDateTime startTimeLocal;
    private String timezone;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public IStravaRun setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    @Override
    public IStravaRun setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalDateTime getStartTimeLocal() {
        return startTimeLocal;
    }

    @Override
    public IStravaRun setStartTimeLocal(LocalDateTime startTimeLocal) {
        this.startTimeLocal = startTimeLocal;
        return this;
    }

    @Override
    public String getTimezone() {
        return timezone;
    }

    @Override
    public IStravaRun setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

}
