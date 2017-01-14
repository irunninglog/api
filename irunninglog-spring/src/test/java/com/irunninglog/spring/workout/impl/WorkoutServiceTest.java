package com.irunninglog.spring.workout.impl;

import com.irunninglog.Gender;
import com.irunninglog.Privacy;
import com.irunninglog.Unit;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.profile.impl.IProfileEntityRepository;
import com.irunninglog.spring.profile.impl.ProfileEntity;
import com.irunninglog.spring.security.impl.IUserEntityRepository;
import com.irunninglog.spring.security.impl.UserEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class WorkoutServiceTest extends AbstractTest {

    @Autowired
    private IProfileEntityRepository profileEntityRepository;
    @Autowired
    private IWorkoutEntityRepository workoutEntityRepository;
    @Autowired
    private IUserEntityRepository userEntityRepository;
    @Autowired
    private WorkoutService workoutsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private DateFormat dateFormat;
    private ProfileEntity profileEntity;
    private UserEntity userEntity;
    private int thisYear;
    private int lastYear;

    @Before
    public void before() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        thisYear = Integer.parseInt(yearFormat.format(new Date(System.currentTimeMillis())));
        lastYear = thisYear - 1;

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setEmail("allan@irunninglog.com");
        profileEntity.setPassword(passwordEncoder.encode("password"));
        profileEntity.setFirstName("Allan");
        profileEntity.setLastName("Lewis");
        profileEntity.setBirthday(LocalDate.now());
        profileEntity.setGender(Gender.Male);
        profileEntity.setPreferredUnits(Unit.English);
        profileEntity.setWeekStart(DayOfWeek.MONDAY);
        profileEntityRepository.save(profileEntity);

        this.profileEntity = profileEntityRepository.findByEmail("allan@irunninglog.com");

        userEntity = userEntityRepository.findByUsername(profileEntity.getEmail());

        WorkoutEntity today = new WorkoutEntity();
        today.setDate(LocalDate.now());
        today.setPrivacy(Privacy.Private);
        today.setProfile(profileEntity);
        workoutEntityRepository.save(today);

        // Noise profile/workout

        ProfileEntity noise = new ProfileEntity();
        noise.setEmail("noise@irunninglog.com");
        noise.setPassword(passwordEncoder.encode("password"));
        noise.setFirstName("Allan");
        noise.setLastName("Lewis");
        noise.setBirthday(LocalDate.now());
        noise.setGender(Gender.Male);
        noise.setPreferredUnits(Unit.English);
        noise.setWeekStart(DayOfWeek.MONDAY);
        profileEntityRepository.save(noise);

        WorkoutEntity noiseWorkout = new WorkoutEntity();
        noiseWorkout.setPrivacy(Privacy.Private);
        noiseWorkout.setDate(LocalDate.now());
        noiseWorkout.setProfile(profileEntityRepository.findByEmail("noise@irunninglog.com"));
        workoutEntityRepository.save(noiseWorkout);
    }

    @After
    public void after() {
        workoutEntityRepository.deleteAll();
        profileEntityRepository.deleteAll();
    }

    @Test
    public void testFindThisWeeksWorkouts() throws ParseException {
        createLastYearsWorkouts();
        createThisWeeksWorkouts();

        ZoneOffset offset = ZonedDateTime.now().getOffset();
        int seconds = offset.getTotalSeconds() / (-60);
        List<WorkoutEntity> workouts = workoutsService.findWorkoutsThisWeek(profileEntity.getId(), profileEntity.getWeekStart(), seconds);

        assertEquals(3, workouts.size());
    }

    private void createThisWeeksWorkouts() {
        Calendar calendar = GregorianCalendar.getInstance();

        int beginShift;
        int endShift;
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                beginShift = -6;
                endShift = 0;
                break;
            case Calendar.MONDAY:
                beginShift = 0;
                endShift = 6;
                break;
            case Calendar.TUESDAY:
                beginShift = -1;
                endShift = 5;
                break;
            case Calendar.WEDNESDAY:
                beginShift = -2;
                endShift = 4;
                break;
            case Calendar.THURSDAY:
                beginShift = -3;
                endShift = 3;
                break;
            case Calendar.FRIDAY:
                beginShift = -4;
                endShift = 2;
                break;
            case Calendar.SATURDAY:
                beginShift = -5;
                endShift = 1;
                break;
            default:
                throw new IllegalArgumentException();
        }

        Calendar calendar1 = GregorianCalendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        calendar1.add(Calendar.DAY_OF_MONTH, beginShift);

        WorkoutEntity one = new WorkoutEntity();
        one.setProfile(profileEntity);
        one.setPrivacy(Privacy.Private);
        one.setDate(create(calendar1.getTime()));
        workoutEntityRepository.save(one);

        Calendar calendar2 = GregorianCalendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);
        calendar2.add(Calendar.DAY_OF_MONTH, endShift);

        WorkoutEntity two = new WorkoutEntity();
        two.setProfile(profileEntity);
        two.setPrivacy(Privacy.Private);
        two.setDate(create(calendar.getTime()));
        workoutEntityRepository.save(two);
    }

    @Test
    public void testFindThisMonthsWorkouts() throws ParseException {
        createLastYearsWorkouts();
        createThisMonthsWorkouts();

        ZoneOffset offset = ZonedDateTime.now().getOffset();
        int seconds = offset.getTotalSeconds() / (-60);
        assertEquals(3, workoutsService.findWorkoutsThisMonth(userEntity.getId(), seconds).size());
    }

    private void createThisMonthsWorkouts() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        WorkoutEntity one = new WorkoutEntity();
        one.setProfile(profileEntity);
        one.setPrivacy(Privacy.Private);
        one.setDate(create(calendar.getTime()));
        workoutEntityRepository.save(one);

        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        WorkoutEntity two = new WorkoutEntity();
        two.setProfile(profileEntity);
        two.setPrivacy(Privacy.Private);
        two.setDate(create(calendar.getTime()));
        workoutEntityRepository.save(two);
    }

    @Test
    public void testFindThisYearsWorkouts() throws ParseException {
        createLastYearsWorkouts();
        createThisYearsWorkouts();

        ZoneOffset offset = ZonedDateTime.now().getOffset();
        int seconds = offset.getTotalSeconds() / (-60);
        assertEquals(4, workoutsService.findWorkoutsThisYear(userEntity.getId(), seconds).size());
    }

    private void createThisYearsWorkouts() throws ParseException {
        WorkoutEntity one = new WorkoutEntity();
        one.setDate(create(new Date(dateFormat.parse(thisYear + "-01-01").getTime())));
        one.setPrivacy(Privacy.Private);
        one.setProfile(profileEntity);
        workoutEntityRepository.save(one);

        WorkoutEntity two = new WorkoutEntity();
        two.setDate(create(new Date(dateFormat.parse(thisYear + "-12-31").getTime())));
        two.setPrivacy(Privacy.Private);
        two.setProfile(profileEntity);
        workoutEntityRepository.save(two);

        WorkoutEntity three = new WorkoutEntity();
        three.setDate(create(new Date(dateFormat.parse(thisYear + "-07-04").getTime())));
        three.setPrivacy(Privacy.Private);
        three.setProfile(profileEntity);
        workoutEntityRepository.save(three);
    }

    @Test
    public void testFindLastYearsWorkouts() throws ParseException {
        createLastYearsWorkouts();

        ZoneOffset offset = ZonedDateTime.now().getOffset();
        int seconds = offset.getTotalSeconds() / (-60);
        assertEquals(2, workoutsService.findWorkoutsLastYear(userEntity.getId(), seconds).size());
    }

    @Test
    public void testFindWorkoutsForMonth() {
        assertEquals(1, workoutsService.findWorkoutsForMonth(profileEntity.getId(), LocalDate.now()).size());
    }

    @Test
    public void tesFfindWorkoutMonthBefore() {
        assertTrue(workoutsService.findWorkoutMonthBefore(profileEntity.getId(), LocalDate.now()) == null);
    }

    @Test
    public void tesFfindWorkoutMonthAfter() {
        assertTrue(workoutsService.findWorkoutMonthAfter(profileEntity.getId(), LocalDate.now()) == null);
    }

    private void createLastYearsWorkouts() throws ParseException {
        WorkoutEntity one = new WorkoutEntity();
        one.setDate(create(new Date(dateFormat.parse(lastYear + "-01-01").getTime())));
        one.setPrivacy(Privacy.Private);
        one.setProfile(profileEntity);
        workoutEntityRepository.save(one);

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.YEAR, -1);

        WorkoutEntity two = new WorkoutEntity();
        two.setDate(create(new Date(calendar.getTime().getTime())));
        two.setPrivacy(Privacy.Private);
        two.setProfile(profileEntity);
        workoutEntityRepository.save(two);
    }

    private LocalDate create(java.util.Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.toLocalDate();
    }

}
