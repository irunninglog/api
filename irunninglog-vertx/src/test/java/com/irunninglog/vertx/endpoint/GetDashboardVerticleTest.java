package com.irunninglog.vertx.endpoint;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.dashboard.IDashboardService;
import com.irunninglog.api.dashboard.IGetDashboardRequest;
import com.irunninglog.api.dashboard.IGetDashboardResponse;
import com.irunninglog.vertx.endpoint.dashboard.GetDashboardVerticle;
import com.irunninglog.vertx.mock.MockDashboardInfo;
import io.vertx.ext.unit.TestContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class GetDashboardVerticleTest extends AbstractVerticleTest {

    @Before
    public void before(TestContext context) {
        IDashboardService dashboardService = Mockito.mock(IDashboardService.class);
        Mockito.when(dashboardService.get(any(Long.class), any(Integer.class))).thenReturn(new MockDashboardInfo());

        GetDashboardVerticle verticle = new GetDashboardVerticle(dashboardService, factory, mapper);
        rule.vertx().deployVerticle(verticle, context.asyncAssertSuccess());
    }

    @Test
    public void ok(TestContext context) {
        rule.vertx().eventBus().<String>send(Endpoint.DASHBOARD_GET.getAddress(), mapper.encode(factory.get(IGetDashboardRequest.class)), context.asyncAssertSuccess(o ->  {
            String s = o.body();
            IGetDashboardResponse response = mapper.decode(s, IGetDashboardResponse.class);

            context.assertEquals(ResponseStatus.OK, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

}
