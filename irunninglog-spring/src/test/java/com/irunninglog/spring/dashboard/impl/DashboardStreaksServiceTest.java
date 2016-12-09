package com.irunninglog.spring.dashboard.impl;

import com.irunninglog.Gender;
import com.irunninglog.Privacy;
import com.irunninglog.Unit;
import com.irunninglog.dashboard.ProgressInfo;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.profile.impl.IProfileEntityRepository;
import com.irunninglog.spring.profile.impl.ProfileEntity;
import com.irunninglog.spring.security.impl.AuthorityEntity;
import com.irunninglog.spring.security.impl.IAuthorityEntityRepository;
import com.irunninglog.spring.security.impl.IUserEntityRepository;
import com.irunninglog.spring.security.impl.UserEntity;
import com.irunninglog.spring.workout.impl.IWorkoutEntityRepository;
import com.irunninglog.spring.workout.impl.WorkoutEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class DashboardStreaksServiceTest extends AbstractTest {

    @Autowired
    private IWorkoutEntityRepository workoutEntityRepository;
    @Autowired
    private IAuthorityEntityRepository authorityEntityRepository;
    @Autowired
    private IUserEntityRepository userEntityRepository;
    @Autowired
    private IProfileEntityRepository profileEntityRepository;
    @Autowired
    private DateService dateService;
    @Autowired
    private DashboardStreaksService streaksService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private ProfileEntity profileEntity;
    private UserEntity userEntity;

    @Before
    public void before() {
        profileEntity = new ProfileEntity();
        profileEntity.setEmail("allan@irunninglog.com");
        profileEntity.setFirstName("First");
        profileEntity.setLastName("Last");
        profileEntity.setPassword(passwordEncoder.encode("password"));
        profileEntity.setWeekStart(DayOfWeek.MONDAY);
        profileEntity.setPreferredUnits(Unit.English);
        profileEntity.setBirthday(LocalDate.now());
        profileEntity.setGender(Gender.Male);

        profileEntity = profileEntityRepository.save(profileEntity);

        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setName("MYPROFILE");
        authorityEntity = authorityEntityRepository.save(authorityEntity);

        userEntity = userEntityRepository.findOne(profileEntity.getId());
        userEntity.getAuthorities().add(authorityEntity);
        userEntity = userEntityRepository.save(userEntity);
    }

    @After
    public void after() {
        workoutEntityRepository.deleteAll();
        userEntityRepository.deleteAll();
        authorityEntityRepository.deleteAll();
    }

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
    public void testThisYearNonCurrentStreak() {
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

        ProgressInfo thsYear = iterator.next();
        assertEquals("This Year", thsYear.getTitle());
        assertEquals("2 day(s)", thsYear.getSubTitle());
        assertEquals("2 workout(s)", thsYear.getTextOne());
        assertEquals(dateService.formatMedium(date.minusDays(1)) + " through " + dateService.formatMedium(date), thsYear.getTextTwo());

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

        ProgressInfo current = iterator.next();
        assertEquals("Current", current.getTitle());
        assertEquals("1 day(s)", current.getSubTitle());
        assertEquals("1 workout(s)", current.getTextOne());
        assertEquals(dateService.formatMedium(date) + " through " + dateService.formatMedium(date), current.getTextTwo());

        ProgressInfo thsYear = iterator.next();
        assertEquals("This Year", thsYear.getTitle());
        assertEquals("2 day(s)", thsYear.getSubTitle());
        assertEquals("2 workout(s)", thsYear.getTextOne());
        assertEquals(dateService.formatMedium(date1.minusDays(1)) + " through " + dateService.formatMedium(date1), thsYear.getTextTwo());

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

    private void saveWorkout(LocalDate localDate) {
        WorkoutEntity entity = new WorkoutEntity();
        entity.setPrivacy(Privacy.Private);
        entity.setDate(localDate);
        entity.setUser(userEntity);

        workoutEntityRepository.save(entity);
    }

}
