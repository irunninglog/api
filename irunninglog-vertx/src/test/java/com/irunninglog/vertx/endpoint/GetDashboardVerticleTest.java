package com.irunninglog.vertx.endpoint;

import com.irunninglog.dashboard.DashboardInfo;
import com.irunninglog.dashboard.DashboardRequest;
import com.irunninglog.dashboard.DashboardResponse;
import com.irunninglog.api.dashboard.IDashboardService;
import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.vertx.endpoint.dashboard.GetDashboardVerticle;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.TestContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class GetDashboardVerticleTest extends AbstractVerticleTest {

    @Before
    public void before(TestContext context) {
        IDashboardService dashboardService = Mockito.mock(IDashboardService.class);
        Mockito.when(dashboardService.get(any(DashboardRequest.class))).thenReturn(new DashboardResponse().setStatus(ResponseStatus.Ok).setBody(new DashboardInfo()));

        GetDashboardVerticle verticle = new GetDashboardVerticle(dashboardService);
        rule.vertx().deployVerticle(verticle, context.asyncAssertSuccess());
    }

    @Test
    public void ok(TestContext context) {
        rule.vertx().eventBus().<String>send(Endpoint.GetDashboard.getAddress(), Json.encode(new DashboardRequest()), context.asyncAssertSuccess(o ->  {
            String s = o.body();
            DashboardResponse response = Json.decodeValue(s, DashboardResponse.class);

            context.assertEquals(ResponseStatus.Ok, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

}
