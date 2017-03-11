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

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public String getBirthday() {
        return null;
    }

    @Override
    public Gender getGender() {
        return null;
    }

    @Override
    public DayOfWeek getWeekStart() {
        return null;
    }

    @Override
    public Unit getPreferredUnits() {
        return null;
    }

    @Override
    public double getWeeklyTarget() {
        return 0;
    }

    @Override
    public double getMonthlyTarget() {
        return 0;
    }

    @Override
    public double getYearlyTarget() {
        return 0;
    }

    @Override
    public long getDefaultRouteId() {
        return 0;
    }

    @Override
    public long getDefaultRunId() {
        return 0;
    }

    @Override
    public long getDefaultShoeId() {
        return 0;
    }

}
