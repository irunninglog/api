package com.irunninglog.spring.streaks;

import com.irunninglog.api.Progress;
import com.irunninglog.api.streaks.IStreak;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class Streak implements IStreak {

    private String startDate;
    private String endDate;
    private Progress progress;
    private int days;
    private int runs;
    private int percentage;

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public IStreak setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public IStreak setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    @Override
    public Progress getProgress() {
        return progress;
    }

    @Override
    public IStreak setProgress(Progress progress) {
        this.progress = progress;
        return this;
    }

    @Override
    public int getDays() {
        return days;
    }

    @Override
    public IStreak setDays(int days) {
        this.days = days;
        return this;
    }

    @Override
    public int getRuns() {
        return runs;
    }

    @Override
    public IStreak setRuns(int runs) {
        this.runs = runs;
        return this;
    }

    @Override
    public int getPercentage() {
        return percentage;
    }

    @Override
    public IStreak setPercentage(int percentage) {
        this.percentage = percentage;
        return this;
    }
}
