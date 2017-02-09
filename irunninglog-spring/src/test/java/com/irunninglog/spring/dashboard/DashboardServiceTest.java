package com.irunninglog.spring.dashboard;

import com.irunninglog.api.dashboard.IDashboardInfo;
import com.irunninglog.api.dashboard.IDashboardService;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ResponseStatusException;
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
        IDashboardInfo info = dashboardService.get(profileEntity.getId(), 0);
        assertNotNull(info);
    }

    @Test
    public void bad() {
        try {
            dashboardService.get(profileEntity.getId() + 1, 0);
            fail("Should have thrown");
        } catch (ResponseStatusException ex) {
            assertEquals(ResponseStatus.NotFound, ex.getResponseStatus());
        }
    }

}
