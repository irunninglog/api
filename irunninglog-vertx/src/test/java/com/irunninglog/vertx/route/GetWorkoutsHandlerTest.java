package com.irunninglog.vertx.route;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.AuthzException;
import com.irunninglog.api.workout.IWorkouts;
import com.irunninglog.vertx.endpoint.workout.GetWorkoutsVerticle;
import com.irunninglog.api.workout.IWorkoutService;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class GetWorkoutsHandlerTest extends AbstractHandlerTest{

    private final IWorkoutService workoutService = Mockito.mock(IWorkoutService.class);

    @Override
    protected void afterBefore(TestContext context) {
        vertx.deployVerticle(new GetWorkoutsVerticle(workoutService, factory, mapper), context.asyncAssertSuccess());

        Mockito.when(workoutService.get(any(Long.class), any(Integer.class)))
                .thenReturn(Mockito.mock(IWorkouts.class));
    }

    @Test
    public void matchNoParameters(TestContext context) throws AuthnException, AuthzException {
        authn();

        context.assertEquals(200, request(context, "/profiles/1/workouts", TOKEN));
    }

    @Test
    public void matchWithParameters(TestContext context) throws AuthnException, AuthzException {
        authn();

        context.assertEquals(200, request(context, "/profiles/1/workouts/12-01-2016", TOKEN));
    }

    @Test
    public void matchNotFound(TestContext context) throws AuthnException, AuthzException {
        authn();

        context.assertEquals(404, request(context, "/profiles/1/workoutss", TOKEN));
    }

}
