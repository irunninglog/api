package com.irunninglog.main;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.vertx.ServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.IOException;

@RunWith(VertxUnitRunner.class)
public class RunningLogApplicationTest {

    private Vertx vertx;

    @Before
    public final void before() throws IOException {
        System.setProperty("dataSource", "org.h2.Driver|jdbc:h2:mem:test;DB_CLOSE_DELAY=-1|sa");
        System.setProperty("jpa", "update|org.hibernate.dialect.H2Dialect");
        System.setProperty("jwt", "issuer|secret");

        vertx = Vertx.vertx();
    }

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void start(TestContext context) {
        ServerVerticle verticle = new ServerVerticle(8889,
                context.asyncAssertSuccess(),
                Mockito.mock(IFactory.class),
                Mockito.mock(IMapper.class),
                Mockito.mock(IAuthenticationService.class));

        vertx.deployVerticle(verticle, context.asyncAssertSuccess());

        new RunningLogApplication().start(vertx, context.asyncAssertSuccess());
    }

}
