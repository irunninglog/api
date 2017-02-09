package com.irunninglog.spring.dashboard;

import com.irunninglog.api.dashboard.IDashboardInfo;
import com.irunninglog.api.dashboard.IProgressInfo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
final class DashboardInfo implements IDashboardInfo {

    private final List<IProgressInfo> progress = new ArrayList<>();
    private final List<IProgressInfo> goals = new ArrayList<>();
    private final List<IProgressInfo> shoes = new ArrayList<>();
    private final List<IProgressInfo> streaks = new ArrayList<>();

    @Override
    public List<IProgressInfo> getProgress() {
        return progress;
    }

    @Override
    public List<IProgressInfo> getGoals() {
        return goals;
    }

    @Override
    public List<IProgressInfo> getShoes() {
        return shoes;
    }

    @Override
    public List<IProgressInfo> getStreaks() {
        return streaks;
    }

}
