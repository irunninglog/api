package com.irunninglog.spring.statistics;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.statistics.*;
import com.irunninglog.spring.util.DateService;
import com.irunninglog.spring.util.DistanceService;
import com.irunninglog.strava.IStravaRun;
import com.irunninglog.strava.IStravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        List<IStravaRun> runs = stravaService.runs(user);

        summary(statistics, runs, offset);

        years(statistics, runs);

        dataSets(statistics, runs, offset);

        return statistics;
    }

    private void dataSets(IStatistics statistics, List<IStravaRun> runs, int offset) {
        Map<String, BigDecimal> map = new TreeMap<>();

        for (IStravaRun run : runs) {
            if (!run.getStartTimeLocal().toLocalDate().isBefore(dateService.yearStart(offset))) {
                LocalDate date = dateService.monthStart(run.getStartTimeLocal());
                BigDecimal bigDecimal = map.get(date.toString());
                if (bigDecimal == null) {
                    bigDecimal = new BigDecimal(BigInteger.ZERO);
                }
                map.put(date.toString(), bigDecimal.add(BigDecimal.valueOf(run.getDistance())));
            }
        }

        Map<String, IDataSet> dataSetMap = new HashMap<>();

        Collection<IDataPoint> points = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : map.entrySet()) {
            LocalDate date = LocalDate.parse(entry.getKey());

            points.add(factory.get(IDataPoint.class)
                    .setDate(date.format(DateTimeFormatter.ofPattern("yyyy-MM")))
                    .setLabel(date.format(DateTimeFormatter.ofPattern("MMM yyyy")))
                    .setValue(distanceService.mileage(entry.getValue().floatValue(), Boolean.FALSE))
                    .setValueFormatted(distanceService.mileage(entry.getValue().floatValue())));
        }

        Collection<IDataPoint> totals = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<String, BigDecimal> entry : map.entrySet()) {
            LocalDate date = LocalDate.parse(entry.getKey());

            total = total.add(entry.getValue());

            totals.add(factory.get(IDataPoint.class)
                    .setDate(date.format(DateTimeFormatter.ofPattern("yyyy-MM")))
                    .setLabel(date.format(DateTimeFormatter.ofPattern("MMM yyyy")))
                    .setValue(distanceService.mileage(total.floatValue(), Boolean.FALSE))
                    .setValueFormatted(distanceService.mileage(total.floatValue())));
        }

        dataSetMap.put("points", factory.get(IDataSet.class).setPoints(points));
        dataSetMap.put("totals", factory.get(IDataSet.class).setPoints(totals));

        statistics.setDataSets(dataSetMap);
    }

    private void years(IStatistics statistics, List<IStravaRun> runs) {
        Map<Integer, BigDecimal> map = new TreeMap<>((o1, o2) -> o2.compareTo(o1));

        for (IStravaRun run : runs) {
            int year = run.getStartTimeLocal().getYear();

            BigDecimal bigDecimal = map.get(year);
            if (bigDecimal == null) {
                bigDecimal = BigDecimal.ZERO;
            }

            map.put(year, bigDecimal.add(BigDecimal.valueOf(run.getDistance())));
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

    private void summary(IStatistics statistics, List<IStravaRun> runs, int offset) {
        BigDecimal thisWeek = BigDecimal.ZERO;
        BigDecimal thisMonth = BigDecimal.ZERO;
        BigDecimal thisYear = BigDecimal.ZERO;
        BigDecimal allTime = BigDecimal.ZERO;

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

        statistics.setSummary(factory.get(ISummary.class)
                        .setThisWeek(distanceService.mileage(thisWeek.floatValue()))
                        .setThisMonth(distanceService.mileage(thisMonth.floatValue()))
                        .setThisYear(distanceService.mileage(thisYear.floatValue()))
                        .setAllTime(distanceService.mileage(allTime.floatValue())));
    }

}
