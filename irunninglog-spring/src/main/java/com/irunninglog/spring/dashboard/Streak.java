package com.irunninglog.spring.dashboard;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

final class Streak {

    private int count;
    private LocalDate startDate;
    private LocalDate endDate;

    int getCount() {
        return count;
    }

    Streak count() {
        count++;
        return this;
    }

    LocalDate getStartDate() {
        return startDate;
    }

    Streak setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    LocalDate getEndDate() {
        return endDate;
    }

    Streak setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    long getSpan() {
        if (getStartDate() == null || getEndDate() == null) {
            return 0;
        } else {
            return getStartDate().until(getEndDate(), ChronoUnit.DAYS) + 1;
        }
    }

}
