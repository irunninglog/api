package com.irunninglog.vertx.ping;

import com.irunninglog.api.ping.IPingService;
import com.irunninglog.vertx.AbstractHandlerTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

@Ignore
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
