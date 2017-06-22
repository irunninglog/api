package com.irunninglog.vertx.ping;

import com.irunninglog.api.IResponse;
import com.irunninglog.api.ping.IPingService;
import com.irunninglog.mock.MockPing;
import com.irunninglog.vertx.AbstractTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class PingTest extends AbstractTest {

    private IPingService pingService;

    @Override
    protected void afterBefore(TestContext context) {
        super.afterBefore(context);

        pingService = Mockito.mock(IPingService.class);

        deploy(new PingVerticle(factory(), mapper(), pingService), context);
    }

    @Test
    public void ping(TestContext context) {
        Mockito.when(pingService.ping()).thenReturn(new MockPing());

        assertEquals(200, get(context, "/api/ping"));
    }

    @Test
    public void pingError1(TestContext context) {
        // For test coverage - tests a verticle exception
        Mockito.when(pingService.ping()).thenThrow(new UnsupportedOperationException("Not implemented"));
        assertEquals(500, get(context, "/api/ping"));
    }

    @Test
    public void pingError2(TestContext context) {
        // For test coverage - tests a handler exception
        Mockito.when(pingService.ping()).thenReturn(new MockPing());
        Mockito.when(factory().get(IResponse.class)).thenReturn(null);

        assertEquals(500, get(context, "/api/ping"));
    }

}
