package com.irunninglog.vertx.workout;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.vertx.AbstractHandlerTest;
import com.irunninglog.vertx.mock.MockWorkouts;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class GetWorkoutsHandlerTest extends AbstractHandlerTest {

    private final IWorkoutService workoutService = Mockito.mock(IWorkoutService.class);

    @Override
    protected void afterBefore(TestContext context) {
        vertx.deployVerticle(new GetWorkoutsVerticle(workoutService, factory, mapper), context.asyncAssertSuccess());

        Mockito.when(workoutService.get(any(Long.class), any(String.class), any(Integer.class)))
                .thenReturn(new MockWorkouts());
    }

    @Test
    public void matchNoParameters(TestContext context) throws AuthnException {
        authn();

        context.assertEquals(200, get(context, "/profiles/1/workouts", TOKEN));
    }

    @Test
    public void matchWithParameters(TestContext context) throws AuthnException {
        authn();

        context.assertEquals(200, get(context, "/profiles/1/workouts/12-01-2016", TOKEN));
    }

    @Test
    public void matchNotFound(TestContext context) throws AuthnException {
        authn();

        context.assertEquals(404, get(context, "/profiles/1/workout", TOKEN));
    }

}
