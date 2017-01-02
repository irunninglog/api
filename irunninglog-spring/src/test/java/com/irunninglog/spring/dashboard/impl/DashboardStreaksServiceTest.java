package com.irunninglog.spring.dashboard.impl;

import com.irunninglog.dashboard.ProgressInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class DashboardStreaksServiceTest extends AbstractDashboardServicesTest {

    @Autowired
    private DashboardStreaksService streaksService;

    @Test
    public void testNoStreak() {
        Collection<ProgressInfo> infos = streaksService.streaks(profileEntity);
        Iterator<ProgressInfo> iterator = infos.iterator();

        ProgressInfo current = iterator.next();
        assertEquals("Current", current.getTitle());
        assertEquals("0 day(s)", current.getSubTitle());
        assertEquals("0 workout(s)", current.getTextOne());

        ProgressInfo thisYear = iterator.next();
        assertEquals("This Year", thisYear.getTitle());
        assertEquals("0 day(s)", thisYear.getSubTitle());
        assertEquals("0 workout(s)", thisYear.getTextOne());

        ProgressInfo ever = iterator.next();
        assertEquals("Ever", ever.getTitle());
        assertEquals("0 day(s)", ever.getSubTitle());
        assertEquals("0 workout(s)", ever.getTextOne());
    }

    @Test
    public void testCurrentStreakToday() {
        LocalDate date = LocalDate.now();
        saveWorkout(date);

        Collection<ProgressInfo> infos = streaksService.streaks(profileEntity);
        Iterator<ProgressInfo> iterator = infos.iterator();

        ProgressInfo current = iterator.next();
        assertEquals("Current", current.getTitle());
        assertEquals("1 day(s)", current.getSubTitle());
        assertEquals("1 workout(s)", current.getTextOne());
        assertEquals(dateService.formatMedium(date) + " through " + dateService.formatMedium(date), current.getTextTwo());

        ProgressInfo thisYear = iterator.next();
        assertEquals("This Year", thisYear.getTitle());
        assertEquals("1 day(s)", thisYear.getSubTitle());
        assertEquals("1 workout(s)", thisYear.getTextOne());
        assertEquals(dateService.formatMedium(date) + " through " + dateService.formatMedium(date), thisYear.getTextTwo());

        ProgressInfo ever = iterator.next();
        assertEquals("Ever", ever.getTitle());
        assertEquals("1 day(s)", ever.getSubTitle());
        assertEquals("1 workout(s)", ever.getTextOne());
        assertEquals(dateService.formatMedium(date) + " through " + dateService.formatMedium(date), ever.getTextTwo());
    }

    @Test
    public void testCurrentStreakEndsToday() {
        LocalDate date = LocalDate.now();
        saveWorkout(date);
        saveWorkout(date.minusDays(1));
        saveWorkout(date.minusDays(2));

        Collection<ProgressInfo> infos = streaksService.streaks(profileEntity);
        Iterator<ProgressInfo> iterator = infos.iterator();

        ProgressInfo current = iterator.next();
        assertEquals("Current", current.getTitle());
        assertEquals("3 day(s)", current.getSubTitle());
        assertEquals("3 workout(s)", current.getTextOne());
        assertEquals(dateService.formatMedium(date.minusDays(2)) + " through " + dateService.formatMedium(date), current.getTextTwo());

        ProgressInfo thisYear = iterator.next();
        assertEquals("This Year", thisYear.getTitle());
        assertEquals("3 day(s)", thisYear.getSubTitle());
        assertEquals("3 workout(s)", thisYear.getTextOne());
        assertEquals(dateService.formatMedium(date.minusDays(2)) + " through " + dateService.formatMedium(date), thisYear.getTextTwo());

        ProgressInfo ever = iterator.next();
        assertEquals("Ever", ever.getTitle());
        assertEquals("3 day(s)", ever.getSubTitle());
        assertEquals("3 workout(s)", ever.getTextOne());
        assertEquals(dateService.formatMedium(date.minusDays(2)) + " through " + dateService.formatMedium(date), ever.getTextTwo());
    }

    @Test
    public void testCurrentStreakYesterday() {
        LocalDate date = LocalDate.now().minusDays(1);
        saveWorkout(date);

        Collection<ProgressInfo> infos = streaksService.streaks(profileEntity);
        Iterator<ProgressInfo> iterator = infos.iterator();

        ProgressInfo current = iterator.next();
        assertEquals("Current", current.getTitle());
        assertEquals("1 day(s)", current.getSubTitle());
        assertEquals("1 workout(s)", current.getTextOne());
        assertEquals(dateService.formatMedium(date) + " through " + dateService.formatMedium(date), current.getTextTwo());

        ProgressInfo thisYear = iterator.next();
        assertEquals("This Year", thisYear.getTitle());
        assertEquals("1 day(s)", thisYear.getSubTitle());
        assertEquals("1 workout(s)", thisYear.getTextOne());
        assertEquals(dateService.formatMedium(date) + " through " + dateService.formatMedium(date), thisYear.getTextTwo());

        ProgressInfo ever = iterator.next();
        assertEquals("Ever", ever.getTitle());
        assertEquals("1 day(s)", ever.getSubTitle());
        assertEquals("1 workout(s)", ever.getTextOne());
        assertEquals(dateService.formatMedium(date) + " through " + dateService.formatMedium(date), ever.getTextTwo());
    }

    @Test
    public void testCurrentStreakEndsYesterday() {
        LocalDate date = LocalDate.now().minusDays(1);
        saveWorkout(date);
        saveWorkout(date.minusDays(1));
        saveWorkout(date.minusDays(2));
        saveWorkout(date.minusDays(3));

        Collection<ProgressInfo> infos = streaksService.streaks(profileEntity);
        Iterator<ProgressInfo> iterator = infos.iterator();

        ProgressInfo current = iterator.next();
        assertEquals("Current", current.getTitle());
        assertEquals("4 day(s)", current.getSubTitle());
        assertEquals("4 workout(s)", current.getTextOne());
        assertEquals(dateService.formatMedium(date.minusDays(3)) + " through " + dateService.formatMedium(date), current.getTextTwo());

        ProgressInfo thsYear = iterator.next();
        assertEquals("This Year", thsYear.getTitle());
        assertEquals("4 day(s)", thsYear.getSubTitle());
        assertEquals("4 workout(s)", thsYear.getTextOne());
        assertEquals(dateService.formatMedium(date.minusDays(3)) + " through " + dateService.formatMedium(date), thsYear.getTextTwo());

        ProgressInfo ever = iterator.next();
        assertEquals("Ever", ever.getTitle());
        assertEquals("4 day(s)", ever.getSubTitle());
        assertEquals("4 workout(s)", ever.getTextOne());
        assertEquals(dateService.formatMedium(date.minusDays(3)) + " through " + dateService.formatMedium(date), ever.getTextTwo());
    }

    @Test
    public void testNonCurrentStream() {
        LocalDate date = LocalDate.now().minusDays(3);
        saveWorkout(date);
        saveWorkout(date.minusDays(1));

        Collection<ProgressInfo> infos = streaksService.streaks(profileEntity);
        Iterator<ProgressInfo> iterator = infos.iterator();

        ProgressInfo current = iterator.next();
        assertEquals("Current", current.getTitle());
        assertEquals("0 day(s)", current.getSubTitle());
        assertEquals("0 workout(s)", current.getTextOne());
        assertEquals("No current streak!", current.getTextTwo());

        Calendar calendar = GregorianCalendar.getInstance();
        boolean earlyInYear = calendar.get(Calendar.DAY_OF_YEAR) < 3;

        ProgressInfo thsYear = iterator.next();
        assertEquals("This Year", thsYear.getTitle());
        assertEquals(earlyInYear ? "0 day(s)" : "2 day(s)", thsYear.getSubTitle());
        assertEquals(earlyInYear ? "0 workout(s)" : "2 workout(s)", thsYear.getTextOne());
        assertEquals(earlyInYear ? "No streaks this year!" : dateService.formatMedium(date.minusDays(1)) + " through " + dateService.formatMedium(date), thsYear.getTextTwo());

        ProgressInfo ever = iterator.next();
        assertEquals("Ever", ever.getTitle());
        assertEquals("2 day(s)", ever.getSubTitle());
        assertEquals("2 workout(s)", ever.getTextOne());
        assertEquals(dateService.formatMedium(date.minusDays(1)) + " through " + dateService.formatMedium(date), ever.getTextTwo());
    }

    @Test
    public void testEverStreak() {
        LocalDate date = LocalDate.now();
        saveWorkout(date);
        saveWorkout(date.minusDays(1));

        LocalDate date1 = date.minusYears(2);
        saveWorkout(date1);
        saveWorkout(date1.minusDays(1));
        saveWorkout(date1.minusDays(2));
        saveWorkout(date1.minusDays(3));
        saveWorkout(date1.minusDays(4));

        Collection<ProgressInfo> infos = streaksService.streaks(profileEntity);
        Iterator<ProgressInfo> iterator = infos.iterator();

        ProgressInfo current = iterator.next();
        assertEquals("Current", current.getTitle());
        assertEquals("2 day(s)", current.getSubTitle());
        assertEquals("2 workout(s)", current.getTextOne());
        assertEquals(dateService.formatMedium(date.minusDays(1)) + " through " + dateService.formatMedium(date), current.getTextTwo());

        ProgressInfo thsYear = iterator.next();
        assertEquals("This Year", thsYear.getTitle());
        assertEquals("2 day(s)", thsYear.getSubTitle());
        assertEquals("2 workout(s)", thsYear.getTextOne());
        assertEquals(dateService.formatMedium(date.minusDays(1)) + " through " + dateService.formatMedium(date), thsYear.getTextTwo());

        ProgressInfo ever = iterator.next();
        assertEquals("Ever", ever.getTitle());
        assertEquals("5 day(s)", ever.getSubTitle());
        assertEquals("5 workout(s)", ever.getTextOne());
        assertEquals(dateService.formatMedium(date1.minusDays(4)) + " through " + dateService.formatMedium(date1), ever.getTextTwo());
    }

    @Test
    public void testThreeDistinctStreaks() {
        LocalDate date = LocalDate.now().minusDays(1);
        saveWorkout(date);

        LocalDate date1 = date.minusDays(2);
        saveWorkout(date1);
        saveWorkout(date1.minusDays(1));

        LocalDate date2 = date.minusYears(2);
        saveWorkout(date2);
        saveWorkout(date2.minusDays(1));
        saveWorkout(date2.minusDays(2));

        Collection<ProgressInfo> infos = streaksService.streaks(profileEntity);
        Iterator<ProgressInfo> iterator = infos.iterator();

        boolean longerStreakLastYear = GregorianCalendar.getInstance().get(Calendar.DAY_OF_YEAR) < 3;

        ProgressInfo current = iterator.next();
        assertEquals("Current", current.getTitle());
        assertEquals("1 day(s)", current.getSubTitle());
        assertEquals("1 workout(s)", current.getTextOne());
        assertEquals(dateService.formatMedium(date) + " through " + dateService.formatMedium(date), current.getTextTwo());

        ProgressInfo thsYear = iterator.next();
        assertEquals("This Year", thsYear.getTitle());
        assertEquals(longerStreakLastYear ? "1 day(s)" : "2 day(s)", thsYear.getSubTitle());
        assertEquals(longerStreakLastYear ? "1 workout(s)" : "2 workout(s)", thsYear.getTextOne());
        assertEquals(longerStreakLastYear ? dateService.formatMedium(date) + " through " + dateService.formatMedium(date) : dateService.formatMedium(date1.minusDays(1)) + " through " + dateService.formatMedium(date1), thsYear.getTextTwo());

        ProgressInfo ever = iterator.next();
        assertEquals("Ever", ever.getTitle());
        assertEquals("3 day(s)", ever.getSubTitle());
        assertEquals("3 workout(s)", ever.getTextOne());
        assertEquals(dateService.formatMedium(date2.minusDays(2)) + " through " + dateService.formatMedium(date2), ever.getTextTwo());
    }

    @Test
    public void testMultipleWorkoutsInOneDay() {
        LocalDate date = LocalDate.now().minusDays(1);
        saveWorkout(date);
        saveWorkout(date.minusDays(1));
        saveWorkout(date.minusDays(1));

        Collection<ProgressInfo> infos = streaksService.streaks(profileEntity);
        Iterator<ProgressInfo> iterator = infos.iterator();

        ProgressInfo current = iterator.next();
        assertEquals("Current", current.getTitle());
        assertEquals("2 day(s)", current.getSubTitle());
        assertEquals("3 workout(s)", current.getTextOne());
        assertEquals(dateService.formatMedium(date.minusDays(1)) + " through " + dateService.formatMedium(date), current.getTextTwo());

        ProgressInfo thsYear = iterator.next();
        assertEquals("This Year", thsYear.getTitle());
        assertEquals("2 day(s)", thsYear.getSubTitle());
        assertEquals("3 workout(s)", thsYear.getTextOne());
        assertEquals(dateService.formatMedium(date.minusDays(1)) + " through " + dateService.formatMedium(date), thsYear.getTextTwo());

        ProgressInfo ever = iterator.next();
        assertEquals("Ever", ever.getTitle());
        assertEquals("2 day(s)", ever.getSubTitle());
        assertEquals("3 workout(s)", ever.getTextOne());
        assertEquals(dateService.formatMedium(date.minusDays(1)) + " through " + dateService.formatMedium(date), ever.getTextTwo());
    }

}
