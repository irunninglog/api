package com.irunninglog.main;

import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.Collections;

public class StaticContentTest extends AbstractTest {

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        return Collections.emptyList();
    }

    @Override
    protected void afterBefore(TestContext context) throws Exception {
        save("allan@irunninglog.com", "password", "MYPROFILE");
    }

    @Test
    public void unauthenticated(TestContext context) {
        // These exist, but without auth, should be denied
        context.assertEquals(401, get(context, "/index.html", null));
        context.assertEquals(401, get(context, "/", null));

        // This doesn't exist, but since have no auth, should be denied
        context.assertEquals(401, get(context, "/indexxx.html", null));
    }

    @Test
    public void ok(TestContext context) {
        context.assertEquals(200, get(context, "/index.html", token("allan@irunninglog.com", "password")));
        context.assertEquals(200, get(context, "/", token("allan@irunninglog.com", "password")));
    }

    @Test
    public void notFound(TestContext context) {
        context.assertEquals(404, get(context, "/indexxx.html", token("allan@irunninglog.com", "password")));
    }

}
