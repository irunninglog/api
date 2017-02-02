package com.irunninglog.profile;

import com.google.common.base.MoreObjects;
import com.irunninglog.Gender;
import com.irunninglog.Unit;

import java.time.DayOfWeek;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class Profile {

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

    public long getId() {
        return id;
    }

    public Profile setId(long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Profile setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Profile setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Profile setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public Profile setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Profile setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public DayOfWeek getWeekStart() {
        return weekStart;
    }

    public Profile setWeekStart(DayOfWeek weekStart) {
        this.weekStart = weekStart;
        return this;
    }

    public Unit getPreferredUnits() {
        return preferredUnits;
    }

    public Profile setPreferredUnits(Unit preferredUnits) {
        this.preferredUnits = preferredUnits;
        return this;
    }

    public double getWeeklyTarget() {
        return weeklyTarget;
    }

    public Profile setWeeklyTarget(double weeklyTarget) {
        this.weeklyTarget = weeklyTarget;
        return this;
    }

    public double getMonthlyTarget() {
        return monthlyTarget;
    }

    public Profile setMonthlyTarget(double monthlyTarget) {
        this.monthlyTarget = monthlyTarget;
        return this;
    }

    public double getYearlyTarget() {
        return yearlyTarget;
    }

    public Profile setYearlyTarget(double yearlyTarget) {
        this.yearlyTarget = yearlyTarget;
        return this;
    }

    public long getDefaultRouteId() {
        return defaultRouteId;
    }

    public Profile setDefaultRouteId(long defaultRouteId) {
        this.defaultRouteId = defaultRouteId;
        return this;
    }

    public long getDefaultRunId() {
        return defaultRunId;
    }

    public Profile setDefaultRunId(long defaultRunId) {
        this.defaultRunId = defaultRunId;
        return this;
    }

    public long getDefaultShoeId() {
        return defaultShoeId;
    }

    public Profile setDefaultShoeId(long defaultShoeId) {
        this.defaultShoeId = defaultShoeId;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("email", email)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("birthday", birthday)
                .add("gender", gender)
                .add("weekStart", weekStart)
                .add("preferredUnits", preferredUnits)
                .add("weeklyTarget", weeklyTarget)
                .add("monthlyTarget", monthlyTarget)
                .add("yearlyTarget", yearlyTarget)
                .add("defaultRouteId", defaultRouteId)
                .add("defaultRunId", defaultRunId)
                .add("defaultShoeId", defaultShoeId)
                .toString();
    }

}
