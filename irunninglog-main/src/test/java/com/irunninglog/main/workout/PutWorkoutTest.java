package com.irunninglog.main.workout;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.workout.IPutWorkoutRequest;
import com.irunninglog.api.workout.IWorkoutEntry;
import com.irunninglog.api.workout.IWorkoutService;
import com.irunninglog.main.AbstractTest;
import com.irunninglog.spring.date.DateService;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.vertx.endpoint.workout.PutWorkoutVerticle;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertNotNull;

public class PutWorkoutTest extends AbstractTest {

    private IFactory factory;
    private IMapper mapper;
    private DateService dateService;
    private ProfileEntity profileEntity;

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        factory = applicationContext.getBean(IFactory.class);
        mapper = applicationContext.getBean(IMapper.class);
        dateService = applicationContext.getBean(DateService.class);

        return Collections.singletonList(new PutWorkoutVerticle(applicationContext.getBean(IFactory.class),
                applicationContext.getBean(IMapper.class),
                applicationContext.getBean(IWorkoutService.class)));
    }

    @Override
    protected void afterBefore(TestContext context) {
        profileEntity = save("allan@irunninglog.com", "password", "MYPROFILE");
    }

    @Test
    public void decode() {
        assertNotNull(mapper.decode("{\"offset\":0,\"profileId\":1,\"workout\":{\"@class\":\"com.irunninglog.spring.workout.WorkoutEntry\",\"id\":-1,\"duration\":3600000,\"distance\":\"10\",\"date\":\"03-09-2017\",\"routeId\":-1,\"runId\":-1,\"shoeId\":-1},\"workout\":{\"id\":-1,\"duration\":3600000,\"distance\":\"10\",\"date\":\"03-09-2017\",\"routeId\":-1,\"runId\":-1,\"shoeId\":-1}}", IPutWorkoutRequest.class));
    }

    @Test
    public void put(TestContext context) throws UnsupportedEncodingException {
        IWorkoutEntry workoutEntry = factory.get(IWorkoutEntry.class)
                .setId(-1)
                .setDate(dateService.format(LocalDate.now()))
                .setDuration(3600000)
                .setDistance("10")
                .setRouteId(-1)
                .setRunId(-1)
                .setShoeId(-1);

        context.assertEquals(200, put(context,
                "/profiles/" + profileEntity.getId() + "/workouts",
                token("allan@irunninglog.com"),
                mapper.encode(workoutEntry)));
    }

}
