package com.irunninglog.vertx;

import io.vertx.ext.unit.TestContext;
import org.junit.Test;

import static com.irunninglog.vertx.ServerVerticle.BODY_SIZE_LIMIT;

public class ServerVerticleTest extends AbstractTest {

    @Test
    public void bodyTooLarge(TestContext context) {
        String contents = "";
        for (int i = 0; i < BODY_SIZE_LIMIT + 1; i++) {
            contents += "1";
        }

        context.assertEquals(413, put(context, "/foo", contents));
    }

    @Test
    public void noVerticleDeployed(TestContext context) {
        context.assertEquals(500, get(context, "/api/ping"));
    }

}
