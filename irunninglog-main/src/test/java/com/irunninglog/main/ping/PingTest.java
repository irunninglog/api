package com.irunninglog.main.ping;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.ping.IPingService;
import com.irunninglog.main.AbstractTest;
import com.irunninglog.vertx.endpoint.ping.PingVerticle;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.Collections;

public class PingTest extends AbstractTest {

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        PingVerticle verticle =
                new PingVerticle(applicationContext.getBean(IFactory.class),
                        applicationContext.getBean(IMapper.class),
                        applicationContext.getBean(IPingService.class));

        return Collections.singletonList(verticle);
    }

    @Override
    protected void afterBefore(TestContext context) {

    }

    @Test
    public void ping(TestContext context) {
        context.assertEquals(200, request(context, "/ping", null));
    }

}
