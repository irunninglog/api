package com.irunninglog.spring.streaks;

import com.irunninglog.api.athletes.IAthlete;
import com.irunninglog.api.progress.Progress;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.api.streaks.IStreak;
import com.irunninglog.api.streaks.IStreaks;
import com.irunninglog.api.streaks.IStreaksService;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StreaksServiceTest extends AbstractTest {

    private IStreaksService streaksService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) throws Exception {
        super.afterBefore(applicationContext);

        streaksService = applicationContext.getBean(IStreaksService.class);

        restTemplate.setAthlete(factory.get(IAthlete.class)
                .setId(-1)
                .setEmail("mock@irunninglog.com")
                .setFirstname("Mock")
                .setLastname("User")
                .setAvatar("https://irunninglog.com/profiles/mock"));
    }

    @Test
    public void current() throws InterruptedException {
        List<IRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now()));
        runs.add(run(LocalDateTime.now().minusDays(1)));
        restTemplate.setRuns(runs.toArray(new IRun[]{}));

        IUser user = stravaApiService.userFromToken("token");
        waitForRuns(user);

        IStreaks streaks = streaksService.getStreaks(user, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        expect(streaks.getCurrent(), runs.get(1).getStartTime(), runs.get(0).getStartTime(), 2, 2, 100, Progress.GOOD);
        expect(streaks.getLongest(), runs.get(1).getStartTime(), runs.get(0).getStartTime(), 2, 2, 100, Progress.GOOD);
        expect(streaks.getThisYear(), runs.get(1).getStartTime(), runs.get(0).getStartTime(), 2, 2, 100, Progress.GOOD);
    }

    @Test
    public void none() throws InterruptedException {
        List<IRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now()));
        restTemplate.setRuns(runs.toArray(new IRun[]{}));

        IUser user = stravaApiService.userFromToken("token");
        waitForRuns(user);

        IStreaks streaks = streaksService.getStreaks(user, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        assertNull(streaks.getCurrent());
        assertNull(streaks.getLongest());
        assertNull(streaks.getThisYear());
    }

    @Test
    public void noCurrentStreak() throws InterruptedException {
        List<IRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now()));
        runs.add(run(LocalDateTime.now().minusYears(1)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(1)));
        restTemplate.setRuns(runs.toArray(new IRun[]{}));

        IUser user = stravaApiService.userFromToken("token");
        waitForRuns(user);

        IStreaks streaks = streaksService.getStreaks(user, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        assertNull(streaks.getCurrent());
        assertNotNull(streaks.getLongest());
        assertNull(streaks.getThisYear());
    }

    @Test
    public void currentStreakMustStartTodayOrYesteday() throws InterruptedException {
        List<IRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now().minusDays(2)));
        runs.add(run(LocalDateTime.now().minusDays(3)));
        restTemplate.setRuns(runs.toArray(new IRun[]{}));

        IUser user = stravaApiService.userFromToken("token");
        waitForRuns(user);

        IStreaks streaks = streaksService.getStreaks(user, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        assertNull(streaks.getCurrent());
    }

    @Test
    public void veryOld() throws InterruptedException {
        List<IRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now().minusYears(2)));
        runs.add(run(LocalDateTime.now().minusYears(2).minusDays(1)));
        restTemplate.setRuns(runs.toArray(new IRun[]{}));

        IUser user = stravaApiService.userFromToken("token");
        waitForRuns(user);

        IStreaks streaks = streaksService.getStreaks(user, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        assertNull(streaks.getCurrent());
        expect(streaks.getLongest(), runs.get(1).getStartTime(), runs.get(0).getStartTime(), 2, 2, 100, Progress.GOOD);
        assertNull(streaks.getThisYear());
    }

    @Test
    public void ok() throws InterruptedException {
        int minusDays = LocalDate.now().getDayOfYear() == 1 ? 0 : 1;
        List<IRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now().minusDays(minusDays)));
        runs.add(run(LocalDateTime.now().minusDays(minusDays + 1)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(1)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(2)));
        runs.add(run(LocalDateTime.now().minusYears(1).minusDays(3)));
        restTemplate.setRuns(runs.toArray(new IRun[]{}));

        IUser user = stravaApiService.userFromToken("token");
        waitForRuns(user);

        IStreaks streaks = streaksService.getStreaks(user, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        expect(streaks.getCurrent(), runs.get(1).getStartTime(), runs.get(0).getStartTime(), 2, 2, 66, Progress.OK);
        expect(streaks.getLongest(), runs.get(4).getStartTime(), runs.get(2).getStartTime(), 3, 3, 100, Progress.GOOD);
        expect(streaks.getThisYear(), runs.get(1).getStartTime(), runs.get(0).getStartTime(), 2, 2, 66, Progress.OK);
    }

    @Test
    public void bad() throws InterruptedException {
        int minusDays = LocalDate.now().getDayOfYear() == 1 ? 0 : 1;
        List<IRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.now().minusDays(minusDays)));
        runs.add(run(LocalDateTime.now().minusDays(minusDays + 1)));
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
        restTemplate.setRuns(runs.toArray(new IRun[]{}));

        IUser user = stravaApiService.userFromToken("token");
        waitForRuns(user);

        IStreaks streaks = streaksService.getStreaks(user, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);
        expect(streaks.getCurrent(), runs.get(1).getStartTime(), runs.get(0).getStartTime(), 2, 2, 18, Progress.BAD);
        expect(streaks.getLongest(), runs.get(13).getStartTime(), runs.get(2).getStartTime(), 11, 12, 100, Progress.GOOD);
        expect(streaks.getThisYear(), runs.get(1).getStartTime(), runs.get(0).getStartTime(), 2, 2, 18, Progress.BAD);
    }

    private void expect(IStreak streak, String startDate, String endDate, int days, int runs, int percentage, Progress progress) {
        startDate = ZonedDateTime.parse(startDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toLocalDate().format(DateTimeFormatter.ISO_DATE);
        endDate = ZonedDateTime.parse(endDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toLocalDate().format(DateTimeFormatter.ISO_DATE);

        assertNotNull(streak);
        assertEquals(startDate, streak.getStartDate());
        assertEquals(endDate, streak.getEndDate());
        assertEquals(days, streak.getDays());
        assertEquals(runs, streak.getRuns());
        assertEquals(percentage, streak.getPercentage());
        assertEquals(progress, streak.getProgress());
    }

}
