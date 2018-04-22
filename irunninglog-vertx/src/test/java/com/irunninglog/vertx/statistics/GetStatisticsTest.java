package com.irunninglog.vertx.statistics;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.statistics.IStatisticsService;
import com.irunninglog.mock.MockUser;
import com.irunninglog.vertx.AbstractTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class GetStatisticsTest extends AbstractTest {

    @Override
    protected void afterBefore(TestContext context) {
        super.afterBefore(context);

        IStatisticsService service = Mockito.mock(IStatisticsService.class);
        deploy(new GetStatisticsVerticle(factory(), mapper(), service), context);
    }

    @Test
    public void getStatistics(TestContext context) throws AuthnException {
        returnFromAuthentication(new MockUser());

        assertEquals(200, get(context, "/api/stats"));
    }

}
