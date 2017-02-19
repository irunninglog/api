package com.irunninglog.main;

import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.Collections;

public class ForbiddenTest extends AbstractTest {

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        return Collections.emptyList();
    }

    @Override
    protected void afterBefore(TestContext context) {

    }

    @Test
    public void forbidden(TestContext context) {
        context.assertEquals(403, get(context, "/forbidden", null));
    }

}
