package com.irunninglog.main.workout;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.main.AbstractTest;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.vertx.endpoint.workout.GetWorkoutsVerticle;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;

public class GetWorkoutsTest extends AbstractTest {

    private final LocalDate now = ZonedDateTime.now().toLocalDate();
    private final LocalDate monthAgo = now.minusMonths(1);

    private ProfileEntity profileEntity;
    private DateService dateService;
    private String token;

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        return Collections.singletonList(new GetWorkoutsVerticle(applicationContext.getBean(IWorkoutService.class),
                applicationContext.getBean(IFactory.class),
                applicationContext.getBean(IMapper.class)));
    }

    @Override
    protected void afterBefore(TestContext context) throws UnsupportedEncodingException {
        profileEntity = save("workouts@irunninglog.com", "password", "MYPROFILE");
        token = token("workouts@irunninglog.com");

        dateService = applicationContext.getBean(DateService.class);

        saveWorkout(profileEntity, now, 0, 60 * 60 * 1000, saveRoute(profileEntity, "route", Boolean.FALSE), null, null);
        saveWorkout(profileEntity, now, 10, 60 * 60 * 1000, null, null, null);
        saveWorkout(profileEntity, monthAgo, 10, 0, null, null, null);
        saveWorkout(profileEntity, monthAgo, 0, 0, null, null, null);
        saveWorkout(profileEntity, monthAgo, 5, 0, null, null, null);
    }

    @Test
    public void thisMonth(TestContext context) {
        context.assertEquals(200, get(context, "/profiles/" + profileEntity.getId() + "/workouts", token));
    }

    @Test
    public void lastMonth(TestContext context) {
        context.assertEquals(200, get(context, "/profiles/" + profileEntity.getId() + "/workouts/" + dateService.format(monthAgo), token));
    }

}
