package com.irunninglog.spring.workout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irunninglog.api.Privacy;
import com.irunninglog.api.workout.IWorkout;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WorkoutSerializeTest extends AbstractTest {

    private ApplicationContext applicationContext;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        this.applicationContext = applicationContext;
    }

    @Test
    public void privacySerialize() throws JsonProcessingException {
        IWorkout privateWorkout = applicationContext.getBean(IWorkout.class).setPrivacy(Privacy.PRIVATE);
        assertTrue(objectMapper.writeValueAsString(privateWorkout).contains("PRIVATE"));

        IWorkout publicWorkout = applicationContext.getBean(IWorkout.class).setPrivacy(Privacy.PUBLIC);
        assertTrue(objectMapper.writeValueAsString(publicWorkout).contains("PUBLIC"));
    }

    @Test
    public void privacyDeserialize() throws IOException {
        assertEquals(Privacy.PRIVATE, objectMapper.readValue("{\"privacy\":\"PRIVATE\"}", Workout.class).getPrivacy());
        assertEquals(Privacy.PUBLIC, objectMapper.readValue("{\"privacy\":\"PUBLIC\"}", Workout.class).getPrivacy());
    }

}
