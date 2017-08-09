package com.irunninglog.spring.streaks;

import com.irunninglog.api.Progress;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.streaks.IStreak;
import com.irunninglog.api.streaks.IStreaks;
import com.irunninglog.api.streaks.IStreaksService;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.strava.IStravaRun;
import com.irunninglog.strava.IStravaService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

public class StreaksServiceTest extends AbstractTest {

    private IStreaksService streaksService;
    private IStravaService stravaService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        streaksService = applicationContext.getBean(IStreaksService.class);
        stravaService = applicationContext.getBean(IStravaService.class);
    }

    @Test
    public void current() {
        List<IStravaRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now()));
        runs.add(run(LocalDateTime.now().minusDays(1)));
        Mockito.when(stravaService.runs(any(IUser.class))).thenReturn(runs);

        IStreaks streaks = streaksService.getStreaks(null, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        expect(streaks.getCurrent(), runs.get(1).getStartTimeLocal(), runs.get(0).getStartTimeLocal(), 2, 2, 100, Progress.GOOD);
        expect(streaks.getLongest(), runs.get(1).getStartTimeLocal(), runs.get(0).getStartTimeLocal(), 2, 2, 100, Progress.GOOD);
        expect(streaks.getThisYear(), runs.get(1).getStartTimeLocal(), runs.get(0).getStartTimeLocal(), 2, 2, 100, Progress.GOOD);
    }

    @Test
    public void none() {
        List<IStravaRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now()));
        Mockito.when(stravaService.runs(any(IUser.class))).thenReturn(runs);

        IStreaks streaks = streaksService.getStreaks(null, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        assertNull(streaks.getCurrent());
        assertNull(streaks.getLongest());
        assertNull(streaks.getThisYear());
    }

    @Test
    public void noCurrentStreak() {
        List<IStravaRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now()));
        runs.add(run(LocalDateTime.now().minusYears(1)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(1)));
        Mockito.when(stravaService.runs(any(IUser.class))).thenReturn(runs);

        IStreaks streaks = streaksService.getStreaks(null, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        assertNull(streaks.getCurrent());
        assertNotNull(streaks.getLongest());
        assertNull(streaks.getThisYear());
    }

    @Test
    public void currentStreakMustStartTodayOrYesteday() {
        List<IStravaRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now().minusDays(2)));
        runs.add(run(LocalDateTime.now().minusDays(3)));
        Mockito.when(stravaService.runs(any(IUser.class))).thenReturn(runs);

        IStreaks streaks = streaksService.getStreaks(null, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        assertNull(streaks.getCurrent());
    }

    @Test
    public void veryOld() {
        List<IStravaRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now().minusYears(2)));
        runs.add(run(LocalDateTime.now().minusYears(2).minusDays(1)));
        Mockito.when(stravaService.runs(any(IUser.class))).thenReturn(runs);

        IStreaks streaks = streaksService.getStreaks(null, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        assertNull(streaks.getCurrent());
        expect(streaks.getLongest(), runs.get(1).getStartTimeLocal(), runs.get(0).getStartTimeLocal(), 2, 2, 100, Progress.GOOD);
        assertNull(streaks.getThisYear());
    }

    @Test
    public void ok() {
        List<IStravaRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now().minusDays(1)));
        runs.add(run(LocalDateTime.now().minusDays(2)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(1)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(2)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(3)));
        Mockito.when(stravaService.runs(any(IUser.class))).thenReturn(runs);

        IStreaks streaks = streaksService.getStreaks(null, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        expect(streaks.getCurrent(), runs.get(1).getStartTimeLocal(), runs.get(0).getStartTimeLocal(), 2, 2, 66, Progress.OK);
        expect(streaks.getLongest(), runs.get(4).getStartTimeLocal(), runs.get(2).getStartTimeLocal(), 3, 3, 100, Progress.GOOD);
        expect(streaks.getThisYear(), runs.get(1).getStartTimeLocal(), runs.get(0).getStartTimeLocal(), 2, 2, 66, Progress.OK);
    }

    @Test
    public void bad() {
        List<IStravaRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now().minusDays(1)));
        runs.add(run(LocalDateTime.now().minusDays(2)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(1)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(2)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(2)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(3)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(4)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(5)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(6)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(7)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(8)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(9)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(10)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(11)));
        Mockito.when(stravaService.runs(any(IUser.class))).thenReturn(runs);

        IStreaks streaks = streaksService.getStreaks(null, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        expect(streaks.getCurrent(), runs.get(1).getStartTimeLocal(), runs.get(0).getStartTimeLocal(), 2, 2, 18, Progress.BAD);
        expect(streaks.getLongest(), runs.get(13).getStartTimeLocal(), runs.get(2).getStartTimeLocal(), 11, 12, 100, Progress.GOOD);
        expect(streaks.getThisYear(), runs.get(1).getStartTimeLocal(), runs.get(0).getStartTimeLocal(), 2, 2, 18, Progress.BAD);
    }

    private void expect(IStreak streak, LocalDateTime startDate, LocalDateTime endDate, int days, int runs, int percentage, Progress progress) {
        assertNotNull(streak);
        assertEquals(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), streak.getStartDate());
        assertEquals(endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), streak.getEndDate());
        assertEquals(days, streak.getDays());
        assertEquals(runs, streak.getRuns());
        assertEquals(percentage, streak.getPercentage());
        assertEquals(progress, streak.getProgress());
    }

    private IStravaRun run(LocalDateTime localDateTime) {
        return new IStravaRun() {
            @Override
            public long getId() {
                return 0;
            }

            @Override
            public IStravaRun setId(long id) {
                return null;
            }

            @Override
            public ZonedDateTime getStartTime() {
                return null;
            }

            @Override
            public IStravaRun setStartTime(ZonedDateTime startTime) {
                return null;
            }

            @Override
            public LocalDateTime getStartTimeLocal() {
                return localDateTime;
            }

            @Override
            public IStravaRun setStartTimeLocal(LocalDateTime startTimeLocal) {
                return null;
            }

            @Override
            public String getTimezone() {
                return null;
            }

            @Override
            public IStravaRun setTimezone(String timezone) {
                return null;
            }

            @Override
            public float getDistance() {
                return 0;
            }

            @Override
            public IStravaRun setDistance(float distance) {
                return null;
            }

            @Override
            public String getShoes() {
                return null;
            }

            @Override
            public IStravaRun setShoes(String gear) {
                return null;
            }
        };
    }

}
