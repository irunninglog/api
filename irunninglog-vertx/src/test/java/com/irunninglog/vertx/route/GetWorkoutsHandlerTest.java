package com.irunninglog.vertx.route;

import com.irunninglog.security.AuthnException;
import com.irunninglog.security.AuthzException;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.vertx.endpoint.workout.GetWorkoutsVerticle;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.workout.Workouts;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class GetWorkoutsHandlerTest extends AbstractHandlerTest{

    private final IWorkoutService workoutService = Mockito.mock(IWorkoutService.class);

    @Override
    protected void afterBefore(TestContext context) {
        vertx.deployVerticle(new GetWorkoutsVerticle(workoutService), context.asyncAssertSuccess());

        Mockito.when(workoutService.get(any(GetWorkoutsRequest.class)))
                .thenReturn(new GetWorkoutsResponse().setStatus(ResponseStatus.Ok).setBody(new Workouts()));
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
