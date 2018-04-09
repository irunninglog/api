package com.irunninglog.spring.statistics;

import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.statistics.IDataPoint;
import com.irunninglog.api.statistics.IStatistics;
import com.irunninglog.api.statistics.IStatisticsService;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.strava.IStravaService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

public class DataSetsTest extends AbstractTest {

    private IStatisticsService statisticsService;
    private IStravaService stravaService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        statisticsService = applicationContext.getBean(IStatisticsService.class);
        stravaService = applicationContext.getBean(IStravaService.class);
    }

    @Test
    public void getDataSet() {
        int year = LocalDate.now().getYear();

        List<IRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.parse((year - 1) + "-09-22T00:00:00"), 16093.44F));
        runs.add(run(LocalDateTime.parse(year + "-09-22T00:00:00"), 16093.44F));
        runs.add(run(LocalDateTime.parse(year + "-09-21T00:00:00"), 16093.44F));
        runs.add(run(LocalDateTime.parse(year + "-08-03T00:00:00"), 16093.44F));
        runs.add(run(LocalDateTime.parse(year + "-08-02T00:00:00"), 16093.44F));
        runs.add(run(LocalDateTime.parse(year + "-08-01T00:00:00"), 16093.44F));
        Mockito.when(stravaService.runs(any(IUser.class))).thenReturn(runs);

        IStatistics statistics = statisticsService.get(null, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);

        assertNotNull(statistics.getDataSet());

        Collection<IDataPoint> points = statistics.getDataSet().getPoints();
        assertNotNull(points);
        assertEquals(3, points.size());

        Iterator<IDataPoint> pointIterator = points.iterator();
        IDataPoint point1 = pointIterator.next();
        assertEquals("09-01-" + (year - 1), point1.getDate());
        assertEquals("10", point1.getValues().get("monthly"));
        assertEquals("10 mi", point1.getValues().get("monthlyFormatted"));
        assertEquals("10", point1.getValues().get("cumulative"));
        assertEquals("10 mi", point1.getValues().get("cumulativeFormatted"));

        IDataPoint point2 = pointIterator.next();
        assertEquals("08-01-" + year, point2.getDate());
        assertEquals("30", point2.getValues().get("monthly"));
        assertEquals("30 mi", point2.getValues().get("monthlyFormatted"));
        assertEquals("40", point2.getValues().get("cumulative"));
        assertEquals("40 mi", point2.getValues().get("cumulativeFormatted"));

        IDataPoint point3 = pointIterator.next();
        assertEquals("09-01-" + year, point3.getDate());
        assertEquals("20", point3.getValues().get("monthly"));
        assertEquals("20 mi", point3.getValues().get("monthlyFormatted"));
        assertEquals("60", point3.getValues().get("cumulative"));
        assertEquals("60 mi", point3.getValues().get("cumulativeFormatted"));
    }

}
