package com.irunninglog.api.streaks;

import com.irunninglog.api.Progress;

public interface IStreak {

    IStreak setStartDate(String startDate);

    IStreak setEndDate(String endDate);

    IStreak setProgress(Progress progress);

    IStreak setDays(int days);

    IStreak setRuns(int runs);

    IStreak setPercentage(int percentage);

    String getStartDate();

    String getEndDate();

    Progress getProgress();

    int getDays();

    int getRuns();

    int getPercentage();

}
