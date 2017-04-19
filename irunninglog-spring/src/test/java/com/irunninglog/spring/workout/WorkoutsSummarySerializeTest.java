package com.irunninglog.spring.workout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irunninglog.api.Progress;
import com.irunninglog.api.workout.IWorkoutsSummary;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WorkoutsSummarySerializeTest extends AbstractTest {

    private ApplicationContext applicationContext;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        this.applicationContext = applicationContext;
    }

    @Test
    public void serialize() throws JsonProcessingException {
        IWorkoutsSummary none = applicationContext.getBean(IWorkoutsSummary.class).setProgress(Progress.NONE);
        assertTrue(objectMapper.writeValueAsString(none).contains("NONE"));

        IWorkoutsSummary bad = applicationContext.getBean(IWorkoutsSummary.class).setProgress(Progress.BAD);
        assertTrue(objectMapper.writeValueAsString(bad).contains("BAD"));

        IWorkoutsSummary ok = applicationContext.getBean(IWorkoutsSummary.class).setProgress(Progress.OK);
        assertTrue(objectMapper.writeValueAsString(ok).contains("OK"));

        IWorkoutsSummary good = applicationContext.getBean(IWorkoutsSummary.class).setProgress(Progress.GOOD);
        assertTrue(objectMapper.writeValueAsString(good).contains("GOOD"));
    }

    @Test
    public void deserialize() throws IOException {
        assertEquals(Progress.NONE, objectMapper.readValue("{\"progress\":\"NONE\"}", WorkoutsSummary.class).getProgress());
        assertEquals(Progress.BAD, objectMapper.readValue("{\"progress\":\"BAD\"}", WorkoutsSummary.class).getProgress());
        assertEquals(Progress.OK, objectMapper.readValue("{\"progress\":\"OK\"}", WorkoutsSummary.class).getProgress());
        assertEquals(Progress.GOOD, objectMapper.readValue("{\"progress\":\"GOOD\"}", WorkoutsSummary.class).getProgress());
    }

}
