package com.irunninglog.api.profile;

import com.irunninglog.api.Gender;
import com.irunninglog.api.Unit;

import java.time.DayOfWeek;

public interface IProfile {

    IProfile setId(long id);

    IProfile setEmail(String email);

    IProfile setFirstName(String firstName);

    IProfile setLastName(String lastName);

    IProfile setBirthday(String birthday);

    IProfile setGender(Gender gender);

    IProfile setWeekStart(DayOfWeek weekStart);

    IProfile setPreferredUnits(Unit preferredUnits);

    IProfile setWeeklyTarget(double weeklyTarget);

    IProfile setMonthlyTarget(double monthlyTarget);

    IProfile setYearlyTarget(double yearlyTarget);

    IProfile setDefaultRouteId(long defaultRouteId);

    IProfile setDefaultRunId(long defaultRunId);

    IProfile setDefaultShoeId(long defaultShoeId);

    long getId();

    String getEmail();

    String getFirstName();

    String getLastName();

    String getBirthday();

    Gender getGender();

    DayOfWeek getWeekStart();

    Unit getPreferredUnits();

    double getWeeklyTarget();

    double getMonthlyTarget();

    double getYearlyTarget();

    long getDefaultRouteId();

    long getDefaultRunId();

    long getDefaultShoeId();
}
