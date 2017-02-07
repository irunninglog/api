package com.irunninglog.vertx.endpoint;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.dashboard.IDashboardInfo;
import com.irunninglog.api.dashboard.IDashboardService;
import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.dashboard.IGetDashboardRequest;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
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
        Mockito.when(dashboardService.get(any(Long.class), any(Integer.class))).thenReturn(Mockito.mock(IDashboardInfo.class));

        GetDashboardVerticle verticle = new GetDashboardVerticle(dashboardService, Mockito.mock(IFactory.class), mapper);
        rule.vertx().deployVerticle(verticle, context.asyncAssertSuccess());
    }

    @Test
    public void ok(TestContext context) {
        rule.vertx().eventBus().<String>send(Endpoint.GetDashboard.getAddress(), Json.encode(Mockito.mock(IGetDashboardRequest.class)), context.asyncAssertSuccess(o ->  {
            String s = o.body();
            IGetDashboardResponse response = Json.decodeValue(s, IGetDashboardResponse.class);

            context.assertEquals(ResponseStatus.Ok, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

}
