package com.irunninglog.spring.statistics;

import com.irunninglog.api.athletes.IAthlete;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.statistics.IStatistics;
import com.irunninglog.api.statistics.IStatisticsService;
import com.irunninglog.api.statistics.ITotalByYear;
import com.irunninglog.date.ApiDate;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.strava.StravaService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StatisticsServiceTest extends AbstractTest {

    private IStatisticsService statisticsService;
    private StravaService stravaService;
    private ApiDate apiDate;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) throws Exception {
        super.afterBefore(applicationContext);

        statisticsService = applicationContext.getBean(IStatisticsService.class);
        stravaService = applicationContext.getBean(StravaService.class);
        apiDate = applicationContext.getBean(ApiDate.class);

        restTemplate.setAthlete(factory.get(IAthlete.class)
                .setId(-1)
                .setEmail("mock@irunninglog.com")
                .setFirstname("Mock")
                .setLastname("User")
                .setAvatar("https://irunninglog.com/profiles/mock"));
    }

    @Test
    public void summary() throws InterruptedException {
        List<IRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now(), 16093.44F));
        runs.add(run(LocalDateTime.now().minusYears(1), 16093.44F));
        restTemplate.setRuns(runs.toArray(new IRun[2]));

        IUser user = stravaService.userFromToken("token");

        waitForRuns(user);

        IStatistics statistics = statisticsService.get(user, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1, null, null);

        assertNotNull(statistics.getSummary());
        assertEquals("10 mi", statistics.getSummary().getThisWeek());
        assertEquals("10 mi", statistics.getSummary().getThisMonth());
        assertEquals("10 mi", statistics.getSummary().getThisYear());
        assertEquals("20 mi", statistics.getSummary().getAllTime());
    }

    @Test
    public void yearly() throws InterruptedException {
        LocalDate thisYear = apiDate.yearStart(ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);

        List<IRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now(), 16093.44F));
        runs.add(run(LocalDateTime.now(), 16093.44F));
        runs.add(run(LocalDateTime.now(), 16093.44F));
        runs.add(run(LocalDateTime.now(), 16093.44F));
        runs.add(run(LocalDateTime.now(), 16093.44F));
        runs.add(run(LocalDateTime.now().minusYears(1), 16093.44F));
        runs.add(run(LocalDateTime.now().minusYears(1), 16093.44F));
        runs.add(run(LocalDateTime.now().minusYears(1), 16093.44F));
        runs.add(run(LocalDateTime.now().minusYears(1), 16093.44F));
        restTemplate.setRuns(runs.toArray(new IRun[]{}));

        IUser user = stravaService.userFromToken("token");

        waitForRuns(user);

        IStatistics statistics = statisticsService.get(user, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1, null, null);

        assertNotNull(statistics.getYears());
        assertEquals(2, statistics.getYears().size());

        Iterator<ITotalByYear> totals = statistics.getYears().iterator();

        ITotalByYear thisYearTotal = totals.next();
        assertEquals(thisYear.getYear(), thisYearTotal.getYear());
        assertEquals("50 mi", thisYearTotal.getTotal());
        assertEquals(100, thisYearTotal.getPercentage());

        ITotalByYear lastYearTotal = totals.next();
        assertEquals(thisYear.getYear() - 1, lastYearTotal.getYear());
        assertEquals("40 mi", lastYearTotal.getTotal());
        assertEquals(80, lastYearTotal.getPercentage());
    }

}
