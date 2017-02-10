package com.irunninglog.spring.profile;

import com.irunninglog.api.Gender;
import com.irunninglog.api.Unit;
import com.irunninglog.api.profile.IProfile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
@Scope("prototype")
final class Profile implements IProfile {

    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String birthday;
    private Gender gender;
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
    public IProfile setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public IProfile setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public IProfile setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public IProfile setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    @Override
    public IProfile setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public IProfile setWeekStart(DayOfWeek weekStart) {
        this.weekStart = weekStart;
        return this;
    }

    @Override
    public IProfile setPreferredUnits(Unit preferredUnits) {
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

}
