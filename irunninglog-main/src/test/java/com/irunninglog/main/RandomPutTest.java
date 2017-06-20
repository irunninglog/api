package com.irunninglog.main;

import io.vertx.ext.unit.TestContext;
import org.junit.Test;

public class RandomPutTest extends AbstractTest {

    @Test
    public void testRandom(TestContext context) {
        context.assertEquals(404, put(context, "/random", null, ""));
    }

}
