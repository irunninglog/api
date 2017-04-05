package com.irunninglog.vertx.route;

import com.irunninglog.api.ping.IPingService;
import com.irunninglog.vertx.endpoint.ping.PingVerticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

public class PingHandlerTest extends AbstractHandlerTest {

    @Test
    public void ping(TestContext context) {
        context.assertEquals(200, get(context, "/ping", ""));
    }

    @Override
    protected void afterBefore(TestContext context) {
        PingVerticle verticle = new PingVerticle(factory, mapper, Mockito.mock(IPingService.class));
        vertx.deployVerticle(verticle, context.asyncAssertSuccess());
    }

}
