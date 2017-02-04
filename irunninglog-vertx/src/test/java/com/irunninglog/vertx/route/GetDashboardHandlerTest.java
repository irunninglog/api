package com.irunninglog.vertx.route;

import com.irunninglog.dashboard.DashboardInfo;
import com.irunninglog.dashboard.DashboardRequest;
import com.irunninglog.dashboard.DashboardResponse;
import com.irunninglog.api.dashboard.IDashboardService;
import com.irunninglog.api.ResponseStatus;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class GetDashboardHandlerTest extends AbstractHandlerTest {

    @Override
    protected void afterBefore(TestContext context) {

    }

    @Test
    public void get(TestContext context) {
        IDashboardService dashboardService = Mockito.mock(IDashboardService.class);
        Mockito.when(dashboardService.get(any(DashboardRequest.class)))
                .thenReturn(new DashboardResponse()
                        .setStatus(ResponseStatus.Ok)
                        .setBody(new DashboardInfo()));

        request(context, "/profiles/1/dashboard", TOKEN);
    }

}
