package com.irunninglog.dashboard;

import com.irunninglog.service.ResponseStatus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DashboardResponseTest {

    @Test
    public void sanity() {
        DashboardInfo info = new DashboardInfo();
        DashboardResponse response = new DashboardResponse().setStatus(ResponseStatus.Ok).setBody(info);

        assertEquals(ResponseStatus.Ok, response.getStatus());
        assertEquals(info, response.getBody());
    }

}
