package com.irunninglog.spring.statistics;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.statistics.*;
import com.irunninglog.spring.util.DateService;
import com.irunninglog.spring.util.DistanceService;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

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
        IStatistics statistics = factory.get(IStatistics.class);
        List<IRun> runs = stravaService.runs(user);

        summary(statistics, runs, offset);

        years(statistics, runs);

        dataSets(statistics, runs);

        return statistics;
    }

    private void dataSets(IStatistics statistics, List<IRun> runs) {
        Map<String, BigDecimal> map = new TreeMap<>();

        for (IRun run : runs) {
            LocalDate date = dateService.monthStart(dateService.toLocalDate(run.getStartTime()));
            BigDecimal bigDecimal = map.get(date.toString());
            if (bigDecimal == null) {
                bigDecimal = new BigDecimal(BigInteger.ZERO);
            }
            map.put(date.toString(), bigDecimal.add(new BigDecimal(run.getDistance())));
        }

        Collection<IDataPoint> points = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<String, BigDecimal> entry : map.entrySet()) {
            LocalDate date = LocalDate.parse(entry.getKey());

            total = total.add(entry.getValue());

            Map<String, String> values = new HashMap<>();
            values.put("monthly", distanceService.mileage(entry.getValue().floatValue(), Boolean.FALSE));
            values.put("monthlyFormatted", distanceService.mileage(entry.getValue().floatValue()));
            values.put("cumulative", distanceService.mileage(total.floatValue(), Boolean.FALSE));
            values.put("cumulativeFormatted", distanceService.mileage(total.floatValue()));

            points.add(factory.get(IDataPoint.class)
                    .setDate(dateService.format(date))
                    .setValues(values));
        }

        statistics.setDataSet(factory.get(IDataSet.class).setPoints(points));
    }

    private void years(IStatistics statistics, List<IRun> runs) {
        Map<Integer, BigDecimal> map = new TreeMap<>((o1, o2) -> o2.compareTo(o1));

        for (IRun run : runs) {
            int year = dateService.toLocalDate(run.getStartTime()).getYear();

            BigDecimal bigDecimal = map.get(year);
            if (bigDecimal == null) {
                bigDecimal = BigDecimal.ZERO;
            }

            map.put(year, bigDecimal.add(new BigDecimal(run.getDistance())));
        }

        BigDecimal max = BigDecimal.ZERO;
        for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
            if (entry.getValue().compareTo(max) > 0) {
                max = entry.getValue();
            }
        }

        List<ITotalByYear> totals = new ArrayList<>();
        for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
            totals.add(factory.get(ITotalByYear.class)
                    .setYear(entry.getKey())
                    .setTotal(distanceService.mileage(entry.getValue().floatValue()))
                    .setPercentage(entry.getValue().multiply(new BigDecimal(100)).divide(max, BigDecimal.ROUND_FLOOR).intValue()));
        }

        statistics.setYears(totals);
    }

    private void summary(IStatistics statistics, List<IRun> runs, int offset) {
        BigDecimal thisWeek = BigDecimal.ZERO;
        BigDecimal thisMonth = BigDecimal.ZERO;
        BigDecimal thisYear = BigDecimal.ZERO;
        BigDecimal allTime = BigDecimal.ZERO;

        for (IRun run : runs) {
            allTime = allTime.add(new BigDecimal(run.getDistance()));

            LocalDate yearStart = dateService.yearStart(offset);
            LocalDate localDate = dateService.toLocalDate(run.getStartTime());
            if (localDate.getYear() == yearStart.getYear()) {
                thisYear = thisYear.add(new BigDecimal(run.getDistance()));
            }

            LocalDate monthStart = dateService.monthStart(offset);
            if (localDate.getYear() == monthStart.getYear() && localDate.getMonth() == monthStart.getMonth()) {
                thisMonth = thisMonth.add(new BigDecimal(run.getDistance()));
            }

            LocalDate weekStart = dateService.weekStart(offset);
            LocalDate nextWeekStart = weekStart.plusDays(7);
            if (!localDate.isBefore(weekStart) && localDate.isBefore(nextWeekStart)) {
                thisWeek = thisWeek.add(new BigDecimal(run.getDistance()));
            }
        }

        statistics.setSummary(factory.get(ISummary.class)
                        .setThisWeek(distanceService.mileage(thisWeek.floatValue()))
                        .setThisMonth(distanceService.mileage(thisMonth.floatValue()))
                        .setThisYear(distanceService.mileage(thisYear.floatValue()))
                        .setAllTime(distanceService.mileage(allTime.floatValue())));
    }

}
