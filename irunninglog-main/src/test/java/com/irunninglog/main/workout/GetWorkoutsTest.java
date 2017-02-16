package com.irunninglog.main.workout;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.main.AbstractTest;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.vertx.endpoint.workout.GetWorkoutsVerticle;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.Collections;

public class GetWorkoutsTest extends AbstractTest {

    private ProfileEntity profileEntity;
    private String token;

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        return Collections.singletonList(new GetWorkoutsVerticle(applicationContext.getBean(IWorkoutService.class),
                applicationContext.getBean(IFactory.class),
                applicationContext.getBean(IMapper.class)));
    }

    @Override
    protected void afterBefore(TestContext context) {
        profileEntity = save("workouts@irunninglog.com", "password", "MYPROFILE");
        token = token("workouts@irunninglog.com", "password");
    }

    @Test
    public void ok(TestContext context) {
        context.assertEquals(200, request(context, "/profiles/" + profileEntity.getId() + "/workouts", token));
    }

}
