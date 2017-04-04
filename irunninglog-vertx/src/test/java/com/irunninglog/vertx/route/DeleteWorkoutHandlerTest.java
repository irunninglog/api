package com.irunninglog.vertx.route;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.vertx.endpoint.workout.DeleteWorkoutVerticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class DeleteWorkoutHandlerTest extends AbstractHandlerTest {

    private final IWorkoutService workoutService = Mockito.mock(IWorkoutService.class);

    @Override
    protected void afterBefore(TestContext context) {
        vertx.deployVerticle(new DeleteWorkoutVerticle(factory, mapper, workoutService));

        Mockito.when(workoutService.ownedBy(any(Long.class), any(Long.class))).thenReturn(Boolean.TRUE);
    }

    @Test
    public void delete(TestContext context) throws AuthnException {
        authn();

        context.assertEquals(200, delete(context, "/profiles/1/workouts/1", TOKEN));
    }

}
