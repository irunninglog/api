package com.irunninglog.spring.dashboard;

import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.dashboard.IGetDashboardRequest;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestResponseTest extends AbstractTest {

    @Test
    public void request() {
        IGetDashboardRequest request = new GetDashboardRequest().setProfileId(1);
        assertEquals(1, request.getProfileId());
    }

    @Test
    public void response() {
        IGetDashboardResponse response = new GetDashboardResponse().setStatus(ResponseStatus.Ok).setBody(null);
        assertEquals(null, response.getBody());
        assertEquals(ResponseStatus.Ok, response.getStatus());
    }

}
