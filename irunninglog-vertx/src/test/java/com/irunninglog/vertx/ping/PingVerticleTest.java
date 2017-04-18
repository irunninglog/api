package com.irunninglog.vertx.ping;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ping.IPingRequest;
import com.irunninglog.api.ping.IPingResponse;
import com.irunninglog.api.ping.IPingService;
import com.irunninglog.vertx.AbstractVerticleTest;
import com.irunninglog.vertx.Envelope;
import com.irunninglog.vertx.mock.MockPing;
import com.irunninglog.vertx.mock.MockUser;
import io.vertx.ext.unit.TestContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PingVerticleTest extends AbstractVerticleTest {

    @Before
    public void before(TestContext context) {
        IPingService pingService = Mockito.mock(IPingService.class);
        Mockito.when(pingService.ping()).thenReturn(new MockPing());

        PingVerticle verticle = new PingVerticle(factory, mapper, pingService);
        rule.vertx().deployVerticle(verticle, context.asyncAssertSuccess());
    }

    @Test
    public void ok(TestContext context) {
        String request = mapper.encode(new Envelope()
                .setRequest(mapper.encode(factory.get(IPingRequest.class)))
                .setUser(new MockUser()));

        rule.vertx().eventBus().<String>send(Endpoint.PING.getAddress(), request, context.asyncAssertSuccess(o ->  {
            String s = o.body();
            IPingResponse response = mapper.decode(s, IPingResponse.class);

            context.assertEquals(ResponseStatus.OK, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

}
