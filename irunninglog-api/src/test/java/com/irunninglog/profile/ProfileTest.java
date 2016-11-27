package com.irunninglog.profile;

import com.irunninglog.Gender;
import com.irunninglog.Unit;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProfileTest {

    private Profile profile;

    @Before
    public void before() {
        profile = new Profile();
        profile.setId(1);
        profile.setEmail("junit@irunninglog.com");
        profile.setFirstName("Junit");
        profile.setLastName("Tester");
        profile.setBirthday("1976-12-31");
        profile.setGender(Gender.Male);
        profile.setWeekStart(DayOfWeek.MONDAY);
        profile.setPreferredUnits(Unit.English);
        profile.setWeeklyTarget(25);
        profile.setMonthlyTarget(100);
        profile.setYearlyTarget(1000);
    }

    @Test
    public void getters() {
        assertEquals(1, profile.getId());
        assertEquals("junit@irunninglog.com", profile.getEmail());
        assertEquals("Junit", profile.getFirstName());
        assertEquals("Tester", profile.getLastName());
        assertEquals("1976-12-31", profile.getBirthday());
        assertEquals(Gender.Male, profile.getGender());
        assertEquals(DayOfWeek.MONDAY, profile.getWeekStart());
        assertEquals(Unit.English, profile.getPreferredUnits());
        assertEquals(25, profile.getWeeklyTarget(), 1E-9);
        assertEquals(100, profile.getMonthlyTarget(), 1E-9);
        assertEquals(1000, profile.getYearlyTarget(), 1E-9);
    }

    @Test
    public void tostring() {
        String s = profile.toString();
        assertTrue(s.contains("id"));
        assertTrue(s.contains("firstName"));
        assertTrue(s.contains("lastName"));
        assertTrue(s.contains("birthday"));
        assertTrue(s.contains("gender"));
        assertTrue(s.contains("weekStart"));
        assertTrue(s.contains("preferredUnits"));
        assertTrue(s.contains("weeklyTarget"));
        assertTrue(s.contains("monthlyTarget"));
        assertTrue(s.contains("yearlyTarget"));
    }

}
