package com.irunninglog.spring.statistics;

import com.irunninglog.api.security.IUser;
import com.irunninglog.api.statistics.IStatistics;
import com.irunninglog.api.statistics.IStatisticsService;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.strava.IStravaRun;
import com.irunninglog.strava.IStravaService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

public class StatisticsServiceTest extends AbstractTest {

    private IStatisticsService statisticsService;
    private IStravaService stravaService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        statisticsService = applicationContext.getBean(IStatisticsService.class);
        stravaService = applicationContext.getBean(IStravaService.class);
    }

    @Test
    public void get() {
        List<IStravaRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now(), 16093.4F));
        runs.add(run(LocalDateTime.now().minusYears(1), 16093.4F));
        Mockito.when(stravaService.runs(any(IUser.class))).thenReturn(runs);

        IStatistics statistics = statisticsService.get(null, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);

        assertNotNull(statistics.getSummary());
        assertEquals("10 mi", statistics.getSummary().getThisWeek());
        assertEquals("10 mi", statistics.getSummary().getThisMonth());
        assertEquals("10 mi", statistics.getSummary().getThisYear());
        assertEquals("20 mi", statistics.getSummary().getAllTime());
    }

}
