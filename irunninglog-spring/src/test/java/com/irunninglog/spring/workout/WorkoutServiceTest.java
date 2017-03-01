package com.irunninglog.spring.workout;

import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.profile.ProfileEntity;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;

public class WorkoutServiceTest extends AbstractTest {

    private IWorkoutService workoutService;
    private ProfileEntity profileEntity;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        workoutService = applicationContext.getBean(IWorkoutService.class);
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

}
