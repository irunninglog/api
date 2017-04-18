package com.irunninglog.vertx.forbidden;

import com.irunninglog.vertx.AbstractHandlerTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;

public class ForbiddenHandlerTest extends AbstractHandlerTest {

    @Override
    protected void afterBefore(TestContext context) {

    }

    @Test
    public void forbidden(TestContext context) {
        context.assertEquals(403, get(context, "/forbidden", ""));
    }

}
