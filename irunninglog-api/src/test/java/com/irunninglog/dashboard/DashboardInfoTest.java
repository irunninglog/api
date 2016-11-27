package com.irunninglog.dashboard;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DashboardInfoTest {

    @Test
    public void getters() {
        DashboardInfo info = new DashboardInfo();

        assertTrue(info.getGoals().isEmpty());
        assertTrue(info.getProgress().isEmpty());
        assertTrue(info.getShoes().isEmpty());
        assertTrue(info.getStreaks().isEmpty());
    }

    @Test
    public void tostring() {
        String s = new DashboardInfo().toString();

        assertTrue(s.contains("goals"));
        assertTrue(s.contains("progress"));
        assertTrue(s.contains("shoes"));
        assertTrue(s.contains("streaks"));
    }

}
