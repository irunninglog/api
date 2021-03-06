package com.irunninglog.spring.statistics;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.statistics.*;
import com.irunninglog.date.ApiDate;
import com.irunninglog.math.ApiMath;
import com.irunninglog.spring.strava.StravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Service
final class StatisticsService implements IStatisticsService {

    private final IFactory factory;
    private final StravaService stravaService;
    private final ApiMath apiMath;
    private final ApiDate apiDate;

    @Autowired
    StatisticsService(IFactory factory, StravaService stravaService, ApiMath apiMath, ApiDate apiDate) {
        this.factory = factory;
        this.stravaService = stravaService;
        this.apiMath = apiMath;
        this.apiDate = apiDate;
    }

    @Override
    public IStatistics get(IUser user, int offset, LocalDate startDate, LocalDate endDate) {
        IStatistics statistics = factory.get(IStatistics.class);
        List<IRun> runs = stravaService.runs(user);

        summary(statistics, runs, offset);

        years(statistics, runs, offset);

        dataSets(statistics, runs, startDate, endDate, offset);

        return statistics;
    }

    private void dataSets(IStatistics statistics, List<IRun> runs, LocalDate startDate, LocalDate endDate, int offset) {
        Map<String, BigDecimal> map = new TreeMap<>();

        for (IRun run : runs) {
            // Run start time is a zoned date time
            LocalDate runDate = apiDate.parseZonedDateAsLocalDate(run.getStartTime(), offset);
            LocalDate monthStartDate = apiDate.monthStart(runDate);

            boolean afterStart = startDate == null || !runDate.isBefore(startDate);
            boolean beforeEnd = endDate == null || !runDate.isAfter(endDate);

            if (afterStart && beforeEnd) {
                BigDecimal bigDecimal = map.get(monthStartDate.toString());
                if (bigDecimal == null) {
                    bigDecimal = new BigDecimal(BigInteger.ZERO);
                }

                map.put(monthStartDate.toString(), bigDecimal.add(new BigDecimal(run.getDistance())));
            }
        }

        Collection<IDataPoint> points = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<String, BigDecimal> entry : map.entrySet()) {
            LocalDate date = LocalDate.parse(entry.getKey());

            total = total.add(entry.getValue());

            Map<String, String> values = new HashMap<>();
            values.put("monthly", apiMath.format(apiMath.round(apiMath.miles(entry.getValue())), ApiMath.FORMAT_PLAIN));
            values.put("monthlyFormatted", apiMath.format(apiMath.round(apiMath.miles(entry.getValue())), ApiMath.FORMAT_FORMATTED_MILEAGE));
            values.put("cumulative", apiMath.format(apiMath.round(apiMath.miles(total)), ApiMath.FORMAT_PLAIN));
            values.put("cumulativeFormatted", apiMath.format(apiMath.round(apiMath.miles(total)), ApiMath.FORMAT_FORMATTED_MILEAGE));

            points.add(factory.get(IDataPoint.class)
                    .setDate(apiDate.format(date))
                    .setValues(values));
        }

        statistics.setDataSet(factory.get(IDataSet.class).setPoints(points));
    }

    private void years(IStatistics statistics, List<IRun> runs,int offset) {
        Map<Integer, BigDecimal> map = new TreeMap<>(Comparator.reverseOrder());

        for (IRun run : runs) {
            int year = apiDate.parseZonedDateAsLocalDate(run.getStartTime(), offset).getYear();

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
                    .setTotal(apiMath.format(apiMath.round(apiMath.miles(entry.getValue())), ApiMath.FORMAT_FORMATTED_MILEAGE))
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

            LocalDate yearStart = apiDate.yearStart(offset);
            LocalDate localDate = apiDate.parseZonedDateAsLocalDate(run.getStartTime(), offset);
            if (localDate.getYear() == yearStart.getYear()) {
                thisYear = thisYear.add(new BigDecimal(run.getDistance()));
            }

            LocalDate monthStart = apiDate.monthStart(offset);
            if (localDate.getYear() == monthStart.getYear() && localDate.getMonth() == monthStart.getMonth()) {
                thisMonth = thisMonth.add(new BigDecimal(run.getDistance()));
            }

            LocalDate weekStart = apiDate.weekStart(offset);
            LocalDate nextWeekStart = weekStart.plusDays(7);
            if (!localDate.isBefore(weekStart) && localDate.isBefore(nextWeekStart)) {
                thisWeek = thisWeek.add(new BigDecimal(run.getDistance()));
            }
        }

        statistics.setSummary(factory.get(ISummary.class)
                        .setThisWeek(apiMath.format(apiMath.round(apiMath.miles(thisWeek)), ApiMath.FORMAT_FORMATTED_MILEAGE))
                        .setThisMonth(apiMath.format(apiMath.round(apiMath.miles(thisMonth)), ApiMath.FORMAT_FORMATTED_MILEAGE))
                        .setThisYear(apiMath.format(apiMath.round(apiMath.miles(thisYear)), ApiMath.FORMAT_FORMATTED_MILEAGE))
                        .setAllTime(apiMath.format(apiMath.round(apiMath.miles(allTime)), ApiMath.FORMAT_FORMATTED_MILEAGE)));
    }

}
