package com.irunninglog.spring.statistics;

import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.statistics.IStatistics;
import com.irunninglog.api.statistics.IStatisticsService;
import com.irunninglog.api.statistics.ITotalByYear;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.util.DateService;
import com.irunninglog.strava.IStravaService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

public class StatisticsServiceTest extends AbstractTest {

    private IStatisticsService statisticsService;
    private IStravaService stravaService;
    private DateService dateService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        statisticsService = applicationContext.getBean(IStatisticsService.class);
        stravaService = applicationContext.getBean(IStravaService.class);
        dateService = applicationContext.getBean(DateService.class);
    }

    @Test
    public void summary() {
        List<IRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now(), 16093.44F));
        runs.add(run(LocalDateTime.now().minusYears(1), 16093.44F));
        Mockito.when(stravaService.runs(any(IUser.class))).thenReturn(runs);

        IStatistics statistics = statisticsService.get(null, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);

        assertNotNull(statistics.getSummary());
        assertEquals("10 mi", statistics.getSummary().getThisWeek());
        assertEquals("10 mi", statistics.getSummary().getThisMonth());
        assertEquals("10 mi", statistics.getSummary().getThisYear());
        assertEquals("20 mi", statistics.getSummary().getAllTime());
    }

    @Test
    public void yearly() {
        LocalDate thisYear = dateService.yearStart(ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);

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
        Mockito.when(stravaService.runs(any(IUser.class))).thenReturn(runs);

        IStatistics statistics = statisticsService.get(null, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);

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
