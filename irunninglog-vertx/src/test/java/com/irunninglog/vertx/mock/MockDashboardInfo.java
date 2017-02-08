package com.irunninglog.vertx.mock;

import com.irunninglog.api.dashboard.IDashboardInfo;
import com.irunninglog.api.dashboard.IProgressInfo;

import java.util.ArrayList;
import java.util.List;

public class MockDashboardInfo implements IDashboardInfo {

    private final List<IProgressInfo> progress = new ArrayList<>();
    private final List<IProgressInfo> shoes = new ArrayList<>();
    private final List<IProgressInfo> goals = new ArrayList<>();
    private final List<IProgressInfo> streaks = new ArrayList<>();

    public List<IProgressInfo> getProgress() {
        return progress;
    }

    public List<IProgressInfo> getShoes() {
        return shoes;
    }

    public List<IProgressInfo> getGoals() {
        return goals;
    }

    public List<IProgressInfo> getStreaks() {
        return streaks;
    }

}
