package com.irunninglog.api.statistics;

import com.irunninglog.api.security.IUser;

import java.time.LocalDate;

public interface IStatisticsService {

    IStatistics get(IUser user, int offset, LocalDate startDate, LocalDate endDate);

}
