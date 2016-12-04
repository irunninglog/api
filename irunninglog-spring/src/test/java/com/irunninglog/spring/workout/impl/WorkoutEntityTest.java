package com.irunninglog.spring.workout.impl;

import com.irunninglog.Privacy;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class WorkoutEntityTest extends AbstractTest {

    @Test
    public void sanity() {
        WorkoutEntity workoutEntity = new WorkoutEntity();
        workoutEntity.setId(1);
        workoutEntity.setDate(LocalDate.now());
        workoutEntity.setDistance(2);
        workoutEntity.setDuration(10000);
        workoutEntity.setPrivacy(Privacy.Private);
        workoutEntity.setShoe(null);

        assertEquals(1, workoutEntity.getId());
        assertNotNull(workoutEntity.getDate());
        assertEquals(2, workoutEntity.getDistance(), 1E-9);
        assertEquals(10000, workoutEntity.getDuration());
        assertEquals(Privacy.Private, workoutEntity.getPrivacy());
        assertNull(workoutEntity.getShoe());
    }

}
