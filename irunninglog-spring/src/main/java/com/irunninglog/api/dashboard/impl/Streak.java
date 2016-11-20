package com.irunninglog.api.dashboard.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

class Streak {

    int count;
    LocalDate startDate;
    LocalDate endDate;

    public int getCount() {
        return count;
    }

    public Streak count() {
        count++;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Streak setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Streak setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public boolean isThisYear() {
        return getEndDate() != null && getEndDate().isAfter(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).minusDays(1));
    }

    public long getSpan() {
        if (getStartDate() == null || getEndDate() == null) {
            return 0;
        } else {
            return getStartDate().until(getEndDate(), ChronoUnit.DAYS) + 1;
        }
    }

}
