package com.irunninglog.spring.statistics;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.statistics.IStatistics;
import com.irunninglog.api.statistics.IStatisticsService;
import com.irunninglog.api.statistics.ISummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
final class StatisticsService implements IStatisticsService {

    private final IFactory factory;

    @Autowired
    StatisticsService(IFactory factory) {
        this.factory = factory;
    }

    @Override
    public IStatistics get(IUser user) {
        return factory.get(IStatistics.class)
                .setSummary(factory.get(ISummary.class)
                        .setThisWeek("50")
                        .setThisMonth("100")
                        .setThisYear("1000"));
    }

}
