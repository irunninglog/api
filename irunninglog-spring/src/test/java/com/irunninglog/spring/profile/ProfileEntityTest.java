package com.irunninglog.spring.profile;

import com.irunninglog.api.Unit;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;

import java.time.DayOfWeek;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProfileEntityTest extends AbstractTest {

    @Test
    public void sanity() {
        ProfileEntity entity = new ProfileEntity();
        entity.setId(1);
        entity.setUsername("allan@irunninglog.com");
        entity.setWeekStart(DayOfWeek.MONDAY);
        entity.setPreferredUnits(Unit.ENGLISH);
        entity.setWeeklyTarget(25);
        entity.setMonthlyTarget(125);
        entity.setYearlyTarget(1500);
        entity.setDefaultRoute(null);
        entity.setDefaultShoe(null);
        entity.setDefaultRun(null);

        assertEquals(1, entity.getId());
        assertEquals("allan@irunninglog.com", entity.getUsername());
        assertEquals(DayOfWeek.MONDAY, entity.getWeekStart());
        assertEquals(Unit.ENGLISH, entity.getPreferredUnits());
        assertEquals(25, entity.getWeeklyTarget(), 1E-9);
        assertEquals(125, entity.getMonthlyTarget(), 1E-9);
        assertEquals(1500, entity.getYearlyTarget(), 1E-9);
        assertNull(entity.getDefaultRoute());
        assertNull(entity.getDefaultRun());
        assertNull(entity.getDefaultShoe());
    }

}
