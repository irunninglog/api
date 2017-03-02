package com.irunninglog.spring.workout;

import com.irunninglog.api.workout.IGetWorkoutsRequest;
import com.irunninglog.api.workout.IGetWorkoutsResponse;
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
        IGetWorkoutsRequest request = applicationContext.getBean(IGetWorkoutsRequest.class).setDate("date");

        assertEquals("date", request.getDate());
    }

    @Test
    public void response() {
        IGetWorkoutsResponse response = applicationContext.getBean(IGetWorkoutsResponse.class);

        assertNull(response.getBody());
    }

}
