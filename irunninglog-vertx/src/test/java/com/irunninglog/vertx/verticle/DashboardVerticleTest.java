package com.irunninglog.vertx.verticle;

import com.irunninglog.dashboard.DashboardInfo;
import com.irunninglog.dashboard.DashboardRequest;
import com.irunninglog.dashboard.DashboardResponse;
import com.irunninglog.dashboard.IDashboardService;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.vertx.Address;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.TestContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class DashboardVerticleTest extends AbstractVertxTest {

    @Before
    public void before(TestContext context) {
        IDashboardService dashboardService = Mockito.mock(IDashboardService.class);
        Mockito.when(dashboardService.get(any(DashboardRequest.class))).thenReturn(new DashboardResponse().setStatus(ResponseStatus.Ok).setBody(new DashboardInfo()));

        DashboardVerticle verticle = new DashboardVerticle(dashboardService);
        rule.vertx().deployVerticle(verticle, context.asyncAssertSuccess());
    }

    @Test
    public void ok(TestContext context) {
        rule.vertx().eventBus().<String>send(Address.DashboardGet.getAddress(), Json.encode(new DashboardRequest()), context.asyncAssertSuccess(o ->  {
            String s = o.body();
            DashboardResponse response = Json.decodeValue(s, DashboardResponse.class);

            context.assertEquals(ResponseStatus.Ok, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

}
