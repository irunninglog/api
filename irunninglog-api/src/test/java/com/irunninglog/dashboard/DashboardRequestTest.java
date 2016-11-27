package com.irunninglog.dashboard;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DashboardRequestTest {

    @Test
    public void sanity() {
        DashboardRequest request = new DashboardRequest().setId(1).setOffset(120);

        assertEquals(1, request.getId());
        assertEquals(120, request.getOffset());
    }

}
