package com.irunninglog.spring.data.impl;

import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.profile.impl.ProfileEntity;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class GoalEntityTest extends AbstractTest {

    @Test
    public void sanity() {
        GoalEntity entity = new GoalEntity();

        entity.setId(1);
        entity.setStartDate(LocalDate.now());
        entity.setEndDate(LocalDate.now());
        entity.setGoal(100);
        entity.setDashboard(Boolean.FALSE);
        entity.setName("Name");
        entity.setDescription("Description");
        entity.setProfile(new ProfileEntity());

        assertNotNull(entity.getStartDate());
        assertNotNull(entity.getEndDate());
        assertNotNull(entity.getProfile());
        assertEquals(1, entity.getId());
        assertEquals("Name", entity.getName());
        assertEquals("Description", entity.getDescription());
        assertEquals(100, entity.getGoal(), 1E-9);
        assertFalse(entity.isDashboard());
    }

}
