package com.irunninglog.main.profile;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.main.AbstractTest;
import com.irunninglog.vertx.profile.GetProfileVerticle;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;

@Ignore
public class GetProfileTest extends AbstractTest {

    @Override
    protected void afterBefore(TestContext context) throws Exception {

    }

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        GetProfileVerticle profileVerticle =
                new GetProfileVerticle(applicationContext.getBean(IFactory.class),
                        applicationContext.getBean(IMapper.class),
                        applicationContext.getBean(IProfileService.class));

        return Collections.singletonList(profileVerticle);
    }

    @Test
    public void notAuthenticated(TestContext context) {
        context.assertEquals(401, get(context, "/profiles/" + 1, null));
    }

    @Test
    public void profile(TestContext context) throws UnsupportedEncodingException {
        context.assertEquals(401,
                get(context, "/profiles/" + 1,
                        ""));
    }

    @Test
    public void unauthorized(TestContext context) throws UnsupportedEncodingException {
        context.assertEquals(401,
                get(context, "/profiles/" + 1 + 1,
                        ""));
    }

    @Test
    public void badToken(TestContext context) {
        context.assertEquals(401,
                get(context, "/profiles/" + 1,
                        "foo"));
    }

    @Test
    public void wrongUser(TestContext context) throws UnsupportedEncodingException {
        context.assertEquals(401,
                get(context, "/profiles/" + 1,
                        ""));
    }

}
