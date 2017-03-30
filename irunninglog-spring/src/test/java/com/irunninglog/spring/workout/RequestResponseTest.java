package com.irunninglog.spring.workout;

import com.irunninglog.api.workout.*;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RequestResponseTest extends AbstractTest {

    private ApplicationContext applicationContext;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        this.applicationContext = applicationContext;
    }

    @Test
    public void request() {
        IGetWorkoutsRequest get = applicationContext.getBean(IGetWorkoutsRequest.class).setDate("date");
        assertEquals("date", get.getDate());

        IPutWorkoutRequest put = applicationContext.getBean(IPutWorkoutRequest.class).setWorkout(null);
        assertNull(put.getWorkout());

        IDeleteWorkoutRequest delete = applicationContext.getBean(IDeleteWorkoutRequest.class).setWorkoutId(1);
        assertEquals(1, delete.getWorkoutId());
    }

    @Test
    public void response() {
        IGetWorkoutsResponse get = applicationContext.getBean(IGetWorkoutsResponse.class);
        assertNull(get.getBody());

        IPutWorkoutResponse put = applicationContext.getBean(IPutWorkoutResponse.class);
        assertNull(put.getBody());

        IDeleteWorkoutResponse delete = applicationContext.getBean(IDeleteWorkoutResponse.class);
        assertNull(delete.getBody());
    }

}
