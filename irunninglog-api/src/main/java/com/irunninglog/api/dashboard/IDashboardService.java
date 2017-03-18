package com.irunninglog.api.dashboard;

@FunctionalInterface
public interface IDashboardService {

    IDashboardInfo get(long profileId, int offset);

}
