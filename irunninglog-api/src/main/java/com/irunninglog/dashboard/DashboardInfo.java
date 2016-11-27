package com.irunninglog.dashboard;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

public final class DashboardInfo {

    private final List<ProgressInfo> progress = new ArrayList<>();
    private final List<ProgressInfo> shoes = new ArrayList<>();
    private final List<ProgressInfo> goals = new ArrayList<>();
    private final List<ProgressInfo> streaks = new ArrayList<>();

    public List<ProgressInfo> getProgress() {
        return progress;
    }

    public List<ProgressInfo> getShoes() {
        return shoes;
    }

    public List<ProgressInfo> getGoals() {
        return goals;
    }

    public List<ProgressInfo> getStreaks() {
        return streaks;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("progress", progress)
                .add("shoes", shoes)
                .add("goals", goals)
                .add("streaks", streaks)
                .toString();
    }

}
