package com.irunninglog.main;

import io.vertx.ext.unit.TestContext;
import org.junit.Test;

public class StaticContentTest extends AbstractTest {

    @Test
    public void notFound(TestContext context) {
        context.assertEquals(404, get(context, "/indexxx.html"));
    }

}
