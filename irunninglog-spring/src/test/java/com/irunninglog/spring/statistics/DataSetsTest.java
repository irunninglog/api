package com.irunninglog.spring.statistics;

import com.irunninglog.api.athletes.IAthlete;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.statistics.IDataPoint;
import com.irunninglog.api.statistics.IStatistics;
import com.irunninglog.api.statistics.IStatisticsService;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.strava.StravaService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DataSetsTest extends AbstractTest {

    private IStatisticsService statisticsService;
    private IUser user;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) throws Exception {
        super.afterBefore(applicationContext);

        statisticsService = applicationContext.getBean(IStatisticsService.class);
        StravaService stravaService = applicationContext.getBean(StravaService.class);

        restTemplate.setAthlete(factory.get(IAthlete.class)
                .setId(-1)
                .setEmail("mock@irunninglog.com")
                .setFirstname("Mock")
                .setLastname("User")
                .setAvatar("https://irunninglog.com/profiles/mock"));


        int year = LocalDate.now().getYear();
        List<IRun> runs = new ArrayList<>();
        runs.add(run((year - 1) + "-09-22T00:00:00Z"));
        runs.add(run(year + "-09-22T17:00:00Z"));
        runs.add(run(year + "-09-21T17:00:00Z"));
        runs.add(run(year + "-08-03T17:00:00Z"));
        runs.add(run(year + "-08-02T17:00:00Z"));
        runs.add(run(year + "-08-01T17:00:00Z"));

        restTemplate.setRuns(runs.toArray(new IRun[6]));

        user = stravaService.userFromToken("token");
    }

    @Test
    public void getDataSet() throws InterruptedException {
        int year = LocalDate.now().getYear();

        waitForRuns(user);

        IStatistics statistics = statisticsService.get(user, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1, null, null);

        assertNotNull(statistics.getDataSet());

        Collection<IDataPoint> points = statistics.getDataSet().getPoints();
        assertNotNull(points);
        assertEquals(3, points.size());

        Iterator<IDataPoint> pointIterator = points.iterator();
        IDataPoint point1 = pointIterator.next();
        assertEquals((year - 1) + "-09-01", point1.getDate());
        assertEquals("10", point1.getValues().get("monthly"));
        assertEquals("10 mi", point1.getValues().get("monthlyFormatted"));
        assertEquals("10", point1.getValues().get("cumulative"));
        assertEquals("10 mi", point1.getValues().get("cumulativeFormatted"));

        IDataPoint point2 = pointIterator.next();
        assertEquals(year + "-08-01", point2.getDate());
        assertEquals("30", point2.getValues().get("monthly"));
        assertEquals("30 mi", point2.getValues().get("monthlyFormatted"));
        assertEquals("40", point2.getValues().get("cumulative"));
        assertEquals("40 mi", point2.getValues().get("cumulativeFormatted"));

        IDataPoint point3 = pointIterator.next();
        assertEquals(year + "-09-01", point3.getDate());
        assertEquals("20", point3.getValues().get("monthly"));
        assertEquals("20 mi", point3.getValues().get("monthlyFormatted"));
        assertEquals("60", point3.getValues().get("cumulative"));
        assertEquals("60 mi", point3.getValues().get("cumulativeFormatted"));
    }

}
