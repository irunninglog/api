package com.irunninglog.vertx.ping;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ping.IPing;
import com.irunninglog.api.ping.IPingService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.vertx.AbstractVerticleTest;
import com.irunninglog.vertx.Envelope;
import io.vertx.ext.unit.TestContext;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

@Ignore
public class PingVerticleTest extends AbstractVerticleTest {

    @Override
    public void afterBefore(TestContext context) {
        IPingService pingService = Mockito.mock(IPingService.class);
        Mockito.when(pingService.ping()).thenReturn(factory.get(IPing.class));

        PingVerticle verticle = new PingVerticle(factory, mapper, pingService);
        rule.vertx().deployVerticle(verticle, context.asyncAssertSuccess());
    }

    @Test
    public void ok(TestContext context) {
        String request = mapper.encode(new Envelope()
                .setRequest(mapper.encode(factory.get(IRequest.class)))
                .setUser(factory.get(IUser.class)));

        rule.vertx().eventBus().<String>send(Endpoint.PING.getAddress(), request, context.asyncAssertSuccess(o ->  {
            String s = o.body();
            IResponse response = mapper.decode(s, IResponse.class);

            context.assertEquals(ResponseStatus.OK, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

}
