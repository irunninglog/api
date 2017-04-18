package com.irunninglog.vertx.workout;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.vertx.AbstractHandlerTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class PutWorkoutHandlerTest extends AbstractHandlerTest {

    private final IWorkoutService workoutService = Mockito.mock(IWorkoutService.class);

    @Override
    protected void afterBefore(TestContext context) {
        vertx.deployVerticle(new PutWorkoutVerticle(factory, mapper, workoutService),
                context.asyncAssertSuccess());

        Mockito.when(workoutService.ownedBy(any(Long.class), any(Long.class))).thenReturn(Boolean.TRUE);
    }

    @Test
    public void put(TestContext context) throws AuthnException {
        authn();

        context.assertEquals(200, put(context, "/profiles/1/workouts", TOKEN, "{}"));
    }

}
