package com.irunninglog.main.ping;

import com.irunninglog.main.AbstractTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;

public class PingTest extends AbstractTest {

    @Test
    public void ping(TestContext context) {
        context.assertEquals(200, get(context, "/api/ping"));
    }

}
