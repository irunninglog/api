package com.irunninglog.spring.profile;

import com.irunninglog.api.Unit;
import com.irunninglog.api.profile.IProfile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
@Scope("prototype")
final class Profile implements IProfile {

    private long id;
    private String username;
    private DayOfWeek weekStart;
    private Unit preferredUnits;
    private double weeklyTarget;
    private double monthlyTarget;
    private double yearlyTarget;
    private long defaultRouteId;
    private long defaultRunId;
    private long defaultShoeId;

    @Override
    public IProfile setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public IProfile setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public IProfile setWeekStart(DayOfWeek weekStart) {
        this.weekStart = weekStart;
        return this;
    }

    @Override
    public IProfile setPreferredUnits(Unit preferredUnits) {
        this.preferredUnits = preferredUnits;
        return this;
    }

    @Override
    public IProfile setWeeklyTarget(double weeklyTarget) {
        this.weeklyTarget = weeklyTarget;
        return this;
    }

    @Override
    public IProfile setMonthlyTarget(double monthlyTarget) {
        this.monthlyTarget = monthlyTarget;
        return this;
    }

    @Override
    public IProfile setYearlyTarget(double yearlyTarget) {
        this.yearlyTarget = yearlyTarget;
        return this;
    }

    @Override
    public IProfile setDefaultRouteId(long defaultRouteId) {
        this.defaultRouteId = defaultRouteId;
        return this;
    }

    @Override
    public IProfile setDefaultRunId(long defaultRunId) {
        this.defaultRunId = defaultRunId;
        return this;
    }

    @Override
    public IProfile setDefaultShoeId(long defaultShoeId) {
        this.defaultShoeId = defaultShoeId;
        return this;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public DayOfWeek getWeekStart() {
        return weekStart;
    }

    @Override
    public Unit getPreferredUnits() {
        return preferredUnits;
    }

    @Override
    public double getWeeklyTarget() {
        return weeklyTarget;
    }

    @Override
    public double getMonthlyTarget() {
        return monthlyTarget;
    }

    @Override
    public double getYearlyTarget() {
        return yearlyTarget;
    }

    @Override
    public long getDefaultRouteId() {
        return defaultRouteId;
    }

    @Override
    public long getDefaultRunId() {
        return defaultRunId;
    }

    @Override
    public long getDefaultShoeId() {
        return defaultShoeId;
    }

}
