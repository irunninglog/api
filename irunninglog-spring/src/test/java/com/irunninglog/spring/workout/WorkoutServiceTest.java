package com.irunninglog.spring.workout;

import com.irunninglog.api.Privacy;
import com.irunninglog.api.Progress;
import com.irunninglog.api.workout.*;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.data.ShoeEntity;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.profile.ProfileEntity;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class WorkoutServiceTest extends AbstractTest {

    private IWorkoutService workoutService;
    private ProfileEntity profileEntity;
    private DateService dateService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        workoutService = applicationContext.getBean(IWorkoutService.class);
        dateService = applicationContext.getBean(DateService.class);

        profileEntity = saveProfile("workouts@irunninglog.com", "password");
    }

    @Test
    public void none() {
        assertEquals(0, workoutService.get(profileEntity.getId(), null, 0).getWorkouts().size());
    }

    @Test
    public void one() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        saveWorkout(zonedDateTime.toLocalDate(), 0, profileEntity);
        assertEquals(1, workoutService.get(profileEntity.getId(), null, -1 * zonedDateTime.getOffset().getTotalSeconds() / 60).getWorkouts().size());
    }

    @Test
    public void two() {
        ShoeEntity shoeEntity = saveShoe("shoe", Boolean.TRUE, profileEntity);

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        saveWorkout(zonedDateTime.toLocalDate(), 10, profileEntity);
        saveWorkout(zonedDateTime.toLocalDate().minusMonths(1), 10, 0, profileEntity, null, null, shoeEntity);

        IWorkouts workouts = workoutService.get(profileEntity.getId(),
                dateService.format(zonedDateTime.toLocalDate().minusMonths(1)),
                -1 * zonedDateTime.getOffset().getTotalSeconds() / 60);

        assertNull(workouts.getPrevious());

        IWorkoutsMonth month = workouts.getNext();
        assertNotNull(month);
        assertEquals(dateService.format(dateService.getMonthStartDate(zonedDateTime.toLocalDate())), month.getDate());
        assertEquals(dateService.formatMonthMedium(zonedDateTime.toLocalDate()), month.getTitle());

        IWorkoutsSummary summary = workouts.getSummary();
        assertNotNull(summary);
        assertEquals(dateService.formatMonthMedium(zonedDateTime.toLocalDate().minusMonths(1)), summary.getTitle());
        assertEquals("10 mi", summary.getMileage());
        assertEquals(1, summary.getCount());
        assertEquals(0, summary.getPercentage());
        assertEquals(Progress.None, summary.getProgress());

        IWorkout workout = workouts.getWorkouts().get(0);
        assertEquals(dateService.format(zonedDateTime.toLocalDate().minusMonths(1)), workout.getDate());
        assertEquals("10 mi", workout.getDistance());
        assertEquals("--", workout.getDuration());
        assertEquals("--", workout.getPace());
        assertEquals(Privacy.Private, workout.getPrivacy());
        assertNotNull(workout.getId());
        assertNull(workout.getRoute());
        assertNull(workout.getRun());

        IWorkoutData data = workout.getShoe();
        assertEquals(shoeEntity.getId(), data.getId());
        assertEquals("shoe", data.getName());
        assertNull(data.getDescription());

        assertEquals(1, workouts.getWorkouts().size());
    }

    @Test
    public void titleFull() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        saveWorkout(zonedDateTime.toLocalDate(), 10, 60 * 60 * 1000, profileEntity);

        IWorkouts workouts = workoutService.get(profileEntity.getId(), null, -1 * zonedDateTime.getOffset().getTotalSeconds() / 60);
        assertEquals("I ran #distance in #duration (#pace pace)", workouts.getWorkouts().iterator().next().getTitle());
    }

    @Test
    public void titleDistance() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        saveWorkout(zonedDateTime.toLocalDate(), 10, profileEntity);

        IWorkouts workouts = workoutService.get(profileEntity.getId(), null, -1 * zonedDateTime.getOffset().getTotalSeconds() / 60);
        assertEquals("I ran for #distance", workouts.getWorkouts().iterator().next().getTitle());
    }

    @Test
    public void titleDuration() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        saveWorkout(zonedDateTime.toLocalDate(), 0, 60 * 60 * 1000, profileEntity);

        IWorkouts workouts = workoutService.get(profileEntity.getId(), null, -1 * zonedDateTime.getOffset().getTotalSeconds() / 60);
        assertEquals("I ran for #duration", workouts.getWorkouts().iterator().next().getTitle());
    }

    @Test
    public void titleMin() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        saveWorkout(zonedDateTime.toLocalDate(), 0, profileEntity);

        IWorkouts workouts = workoutService.get(profileEntity.getId(), null, -1 * zonedDateTime.getOffset().getTotalSeconds() / 60);
        assertEquals("I went for a run", workouts.getWorkouts().iterator().next().getTitle());
    }

}
