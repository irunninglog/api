package com.irunninglog.vertx.mock;

import com.irunninglog.api.Gender;
import com.irunninglog.api.Unit;
import com.irunninglog.api.profile.IProfile;

import java.time.DayOfWeek;

@SuppressWarnings("unused")
public class MockProfile implements IProfile {

    private long id;

    @Override
    public IProfile setId(long id) {
        this.id = id;
        return this;
    }

    public long getId() {
        return id;
    }

    @Override
    public IProfile setEmail(String email) {
        return this;
    }

    @Override
    public IProfile setFirstName(String firstName) {
        return this;
    }

    @Override
    public IProfile setLastName(String lastName) {
        return this;
    }

    @Override
    public IProfile setBirthday(String birthday) {
        return null;
    }

    @Override
    public IProfile setGender(Gender gender) {
        return this;
    }

    @Override
    public IProfile setWeekStart(DayOfWeek weekStart) {
        return this;
    }

    @Override
    public IProfile setPreferredUnits(Unit preferredUnits) {
        return this;
    }

    @Override
    public IProfile setWeeklyTarget(double weeklyTarget) {
        return this;
    }

    @Override
    public IProfile setMonthlyTarget(double monthlyTarget) {
        return this;
    }

    @Override
    public IProfile setYearlyTarget(double yearlyTarget) {
        return this;
    }

    @Override
    public IProfile setDefaultRouteId(long defaultRouteId) {
        return this;
    }

    @Override
    public IProfile setDefaultRunId(long defaultRunId) {
        return this;
    }

    @Override
    public IProfile setDefaultShoeId(long defaultShoeId) {
        return this;
    }

}
