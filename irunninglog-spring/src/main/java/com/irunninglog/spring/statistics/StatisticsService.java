package com.irunninglog.spring.statistics;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.statistics.IStatistics;
import com.irunninglog.api.statistics.IStatisticsService;
import com.irunninglog.api.statistics.ISummary;
import com.irunninglog.spring.util.DateService;
import com.irunninglog.spring.util.DistanceService;
import com.irunninglog.strava.IStravaRun;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
final class StatisticsService implements IStatisticsService {

    private final IFactory factory;
    private final IStravaService stravaService;
    private final DistanceService distanceService;
    private final DateService dateService;

    @Autowired
    StatisticsService(IFactory factory, IStravaService service, DistanceService distanceService, DateService dateService) {
        this.factory = factory;
        this.stravaService = service;
        this.distanceService = distanceService;
        this.dateService = dateService;
    }

    @Override
    public IStatistics get(IUser user, int offset) {
        BigDecimal thisWeek = BigDecimal.ZERO;
        BigDecimal thisMonth = BigDecimal.ZERO;
        BigDecimal thisYear = BigDecimal.ZERO;
        BigDecimal allTime = BigDecimal.ZERO;

        List<IStravaRun> runs = stravaService.runs(user);
        for (IStravaRun run : runs) {
            allTime = allTime.add(BigDecimal.valueOf(run.getDistance()));

            LocalDate yearStart = dateService.yearStart(offset);
            if (run.getStartTimeLocal().getYear() == yearStart.getYear()) {
                thisYear = thisYear.add(BigDecimal.valueOf(run.getDistance()));
            }

            LocalDate monthStart = dateService.monthStart(offset);
            if (run.getStartTimeLocal().getYear() == monthStart.getYear() && run.getStartTimeLocal().getMonth() == monthStart.getMonth()) {
                thisMonth = thisMonth.add(BigDecimal.valueOf(run.getDistance()));
            }

            LocalDate weekStart = dateService.weekStart(offset);
            LocalDate nextWeekStart = weekStart.plusDays(7);
            if (!run.getStartTimeLocal().toLocalDate().isBefore(weekStart) && run.getStartTimeLocal().toLocalDate().isBefore(nextWeekStart)) {
                thisWeek = thisWeek.add(BigDecimal.valueOf(run.getDistance()));
            }
        }

        return factory.get(IStatistics.class)
                .setSummary(factory.get(ISummary.class)
                        .setThisWeek(distanceService.mileage(thisWeek.floatValue()))
                        .setThisMonth(distanceService.mileage(thisMonth.floatValue()))
                        .setThisYear(distanceService.mileage(thisYear.floatValue()))
                        .setAllTime(distanceService.mileage(allTime.floatValue())));
    }

}
