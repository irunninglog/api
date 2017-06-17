package com.irunninglog.vertx.ping;

import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ping.IPing;
import com.irunninglog.api.ping.IPingService;
import com.irunninglog.vertx.AbstractTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class PingTest extends AbstractTest {

    @Override
    protected void afterBefore(TestContext context) {
        super.afterBefore(context);

        IPingService pingService = Mockito.mock(IPingService.class);
        Mockito.when(pingService.ping()).thenReturn(Mockito.mock(IPing.class));

        deploy(new PingVerticle(factory(), mapper(), pingService), context);
    }

    @Test
    public void ping(TestContext context) {
        setResponseCode(ResponseStatus.OK);

        assertEquals(200, get(context, "/api/ping"));
    }

    @Test
    public void pingError(TestContext context) {
        // Response code not set; tests exception condition in AbstractRouteHandler
        assertEquals(500, get(context, "/api/ping"));
    }

}
