package com.irunninglog.api.dashboard;

import java.util.List;

public interface IDashboardInfo {

    List<IProgressInfo> getProgress();

    List<IProgressInfo> getGoals();

    List<IProgressInfo> getShoes();

    List<IProgressInfo> getStreaks();

}
