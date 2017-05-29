package com.irunninglog.main.workout;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.main.AbstractTest;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.spring.workout.WorkoutEntity;
import com.irunninglog.vertx.workout.DeleteWorkoutVerticle;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

public class DeleteWorkoutTest extends AbstractTest {

    private ProfileEntity profileEntity;

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        return Collections.singletonList(new DeleteWorkoutVerticle(applicationContext.getBean(IFactory.class),
                applicationContext.getBean(IMapper.class),
                applicationContext.getBean(IWorkoutService.class)));
    }

    @Override
    protected void afterBefore(TestContext context) {
        profileEntity = save("allan@irunninglog.com");
    }

    @Test
    public void delete(TestContext context) throws UnsupportedEncodingException {
        WorkoutEntity workoutEntity = saveWorkout(profileEntity, LocalDate.now(), 0, 0, null, null, null);

        context.assertEquals(401, delete(context, "/profiles/" + profileEntity.getId() + "/workouts/" + workoutEntity.getId() , ""));
    }

}
