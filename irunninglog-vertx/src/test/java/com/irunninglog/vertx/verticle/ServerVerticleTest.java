package com.irunninglog.vertx.verticle;

import io.vertx.ext.unit.TestContext;
import org.junit.Test;

public class ServerVerticleTest extends AbstractVertxTest {

    @Test
    public void sanity(TestContext context) {
        ServerVerticle serverVerticle = new ServerVerticle(-1);
        rule.vertx().deployVerticle(serverVerticle, context.asyncAssertSuccess(s -> context.assertTrue(Boolean.TRUE)));
    }

}
