package com.irunninglog.api.profile;

import com.irunninglog.api.Unit;

import java.time.DayOfWeek;

public interface IProfile {

    IProfile setId(long id);

    IProfile setUsername(String email);

    IProfile setWeekStart(DayOfWeek weekStart);

    IProfile setPreferredUnits(Unit preferredUnits);

    IProfile setWeeklyTarget(double weeklyTarget);

    IProfile setMonthlyTarget(double monthlyTarget);

    IProfile setYearlyTarget(double yearlyTarget);

    IProfile setDefaultRouteId(long defaultRouteId);

    IProfile setDefaultRunId(long defaultRunId);

    IProfile setDefaultShoeId(long defaultShoeId);

    IProfile setFirstName(String firstName);

    IProfile setLastName(String lastName);

    IProfile setAvatar(String avatar);

    long getId();

    String getUsername();

    DayOfWeek getWeekStart();

    Unit getPreferredUnits();

    double getWeeklyTarget();

    double getMonthlyTarget();

    double getYearlyTarget();

    long getDefaultRouteId();

    long getDefaultRunId();

    long getDefaultShoeId();

    String getFirstName();

    String getLastName();

    String getAvatar();

}
