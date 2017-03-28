package com.irunninglog.vertx.route;

import com.irunninglog.api.dashboard.IDashboardInfo;
import com.irunninglog.api.dashboard.IDashboardService;
import com.irunninglog.api.security.AuthnException;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class GetDashboardHandlerTest extends AbstractHandlerTest {

    @Override
    protected void afterBefore(TestContext context) {

    }

    @Test
    public void get(TestContext context) throws AuthnException {
        authn();

        IDashboardService dashboardService = Mockito.mock(IDashboardService.class);
        Mockito.when(dashboardService.get(any(Long.class), any(Integer.class)))
                .thenReturn(Mockito.mock(IDashboardInfo.class));

        get(context, "/profiles/1/dashboard", TOKEN);
    }

}
