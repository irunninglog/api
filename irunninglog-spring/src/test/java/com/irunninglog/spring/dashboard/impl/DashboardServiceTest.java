package com.irunninglog.spring.dashboard.impl;

import com.irunninglog.dashboard.DashboardRequest;
import com.irunninglog.dashboard.DashboardResponse;
import com.irunninglog.api.dashboard.IDashboardService;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.service.ResponseStatusException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class DashboardServiceTest extends AbstractDashboardServicesTest {

    @Autowired
    private IDashboardService dashboardService;

    @Test
    public void good() {
        DashboardRequest request = new DashboardRequest().setId(profileEntity.getId());
        DashboardResponse response = dashboardService.get(request);
        assertNotNull(response.getBody());
    }

    @Test
    public void bad() {
        try {
            DashboardRequest request = new DashboardRequest().setId(profileEntity.getId() + 1);
            dashboardService.get(request);
            fail("Should have thrown");
        } catch (ResponseStatusException ex) {
            assertEquals(ResponseStatus.NotFound, ex.getResponseStatus());
        }
    }

}
