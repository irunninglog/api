package com.irunninglog.spring.strava;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DataObjectsTest {

    @Test
    public void accessToken() {
        DataObjectAccessToken token = new DataObjectAccessToken();
        token.setAccessToken("access token");
        token.setAthlete(null);
        token.setTokenType("token type");

        assertNull(token.getAthlete());
        assertEquals("access token", token.getAccessToken());
        assertEquals("token type", token.getTokenType());
    }

    @Test
    public void summaryActivity() {
        DataObjectSummaryActivity activity = new DataObjectSummaryActivity();
        activity.setId(1);
        activity.setDistance(10);
        activity.setGearId("gear id");
        activity.setMovingTime(10000);
        activity.setStartDate("start date");
        activity.setType("type");
        activity.setName("name");

        assertEquals(1, activity.getId());
        assertEquals(10, activity.getDistance(), 1E-9);
        assertEquals("gear id", activity.getGearId());
        assertEquals("start date", activity.getStartDate());
        assertEquals(10000, activity.getMovingTime());
        assertEquals("type", activity.getType());
        assertEquals("name", activity.getName());
    }

}
