package com.irunninglog.main;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.http.ServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@RunWith(VertxUnitRunner.class)
public class RunningLogApplicationTest {

    private Vertx vertx;

    @Before
    public final void before() throws IOException {
        System.setProperty("env", "file:///" + new ClassPathResource("application.properties").getFile().getAbsolutePath());

        vertx = Vertx.vertx();
    }

    @After
    public void after(TestContext context) {
        System.clearProperty("env");

        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void start(TestContext context) {
        ServerVerticle verticle = new ServerVerticle(8889,
                context.asyncAssertSuccess(),
                Mockito.mock(IFactory.class),
                Mockito.mock(IMapper.class));

        vertx.deployVerticle(verticle, context.asyncAssertSuccess());

        new RunningLogApplication().start(vertx, context.asyncAssertSuccess());
    }

}
