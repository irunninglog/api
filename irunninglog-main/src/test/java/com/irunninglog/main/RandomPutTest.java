package com.irunninglog.main;

import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.Collections;

public class RandomPutTest extends AbstractTest {

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        return Collections.emptyList();
    }

    @Override
    protected void afterBefore(TestContext context) throws Exception {

    }

    @Test
    public void testRandom(TestContext context) {
        context.assertEquals(404, put(context, "/random", null, ""));
    }

}
