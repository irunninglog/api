package com.irunninglog.api.statistics;

import com.irunninglog.api.security.IUser;

public interface IStatisticsService {

    IStatistics get(IUser user, int offset);

}
