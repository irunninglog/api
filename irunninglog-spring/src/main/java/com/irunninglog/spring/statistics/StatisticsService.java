package com.irunninglog.spring.statistics;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.statistics.*;
import com.irunninglog.date.ApiDate;
import com.irunninglog.math.ApiMath;
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

    @Autowired
    StatisticsService(IFactory factory, IStravaService service) {
        this.factory = factory;
        this.stravaService = service;
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
            // Run start time is a zoned date time
            LocalDate date = ApiDate.monthStart(ApiDate.parseAsLocalDate(run.getStartTime()));

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
            values.put("monthly", ApiMath.format(ApiMath.round(ApiMath.miles(entry.getValue())), ApiMath.FORMAT_PLAIN));
            values.put("monthlyFormatted", ApiMath.format(ApiMath.round(ApiMath.miles(entry.getValue())), ApiMath.FORMAT_FORMATTED_MILEAGE));
            values.put("cumulative", ApiMath.format(ApiMath.round(ApiMath.miles(total)), ApiMath.FORMAT_PLAIN));
            values.put("cumulativeFormatted", ApiMath.format(ApiMath.round(ApiMath.miles(total)), ApiMath.FORMAT_FORMATTED_MILEAGE));

            points.add(factory.get(IDataPoint.class)
                    .setDate(ApiDate.format(date))
                    .setValues(values));
        }

        statistics.setDataSet(factory.get(IDataSet.class).setPoints(points));
    }

    private void years(IStatistics statistics, List<IRun> runs) {
        Map<Integer, BigDecimal> map = new TreeMap<>((o1, o2) -> o2.compareTo(o1));

        for (IRun run : runs) {
            int year = ApiDate.parseAsLocalDate(run.getStartTime()).getYear();

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
                    .setTotal(ApiMath.format(ApiMath.round(ApiMath.miles(entry.getValue())), ApiMath.FORMAT_FORMATTED_MILEAGE))
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

            LocalDate yearStart = ApiDate.yearStart(offset);
            LocalDate localDate = ApiDate.parseAsLocalDate(run.getStartTime());
            if (localDate.getYear() == yearStart.getYear()) {
                thisYear = thisYear.add(new BigDecimal(run.getDistance()));
            }

            LocalDate monthStart = ApiDate.monthStart(offset);
            if (localDate.getYear() == monthStart.getYear() && localDate.getMonth() == monthStart.getMonth()) {
                thisMonth = thisMonth.add(new BigDecimal(run.getDistance()));
            }

            LocalDate weekStart = ApiDate.weekStart(offset);
            LocalDate nextWeekStart = weekStart.plusDays(7);
            if (!localDate.isBefore(weekStart) && localDate.isBefore(nextWeekStart)) {
                thisWeek = thisWeek.add(new BigDecimal(run.getDistance()));
            }
        }

        statistics.setSummary(factory.get(ISummary.class)
                        .setThisWeek(ApiMath.format(ApiMath.round(ApiMath.miles(thisWeek)), ApiMath.FORMAT_FORMATTED_MILEAGE))
                        .setThisMonth(ApiMath.format(ApiMath.round(ApiMath.miles(thisMonth)), ApiMath.FORMAT_FORMATTED_MILEAGE))
                        .setThisYear(ApiMath.format(ApiMath.round(ApiMath.miles(thisYear)), ApiMath.FORMAT_FORMATTED_MILEAGE))
                        .setAllTime(ApiMath.format(ApiMath.round(ApiMath.miles(allTime)), ApiMath.FORMAT_FORMATTED_MILEAGE)));
    }

}
