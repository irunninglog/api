package com.irunninglog.vertx.route;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.AuthzException;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.vertx.endpoint.workout.PutWorkoutVerticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

public class PutWorkoutHandlerTest extends AbstractHandlerTest {

    private final IWorkoutService workoutService = Mockito.mock(IWorkoutService.class);

    @Override
    protected void afterBefore(TestContext context) {
        vertx.deployVerticle(new PutWorkoutVerticle(factory, mapper, workoutService),
                context.asyncAssertSuccess());
    }

    @Test
    public void put(TestContext context) throws AuthnException, AuthzException {
        authn();

        context.assertEquals(200, put(context, "/profiles/1/workouts", TOKEN, "{}"));
    }

}
