package com.irunninglog.main.profile;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.main.AbstractTest;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.vertx.endpoint.profile.GetProfileVerticle;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;

public class GetProfileTest extends AbstractTest {

    private ProfileEntity profile;
    private ProfileEntity admin;

    @Override
    protected void afterBefore(TestContext context) {
        profile = save("allan@irunninglog.com", "password", "MYPROFILE");
        admin = save("admin@irunninglog.com", "password", "ADMIN");
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
        context.assertEquals(401, get(context, "/profiles/" + profile.getId(), null));
    }

    @Test
    public void profile(TestContext context) throws UnsupportedEncodingException {
        context.assertEquals(200,
                get(context, "/profiles/" + profile.getId(),
                        token("allan@irunninglog.com")));
    }

    @Test
    public void admin(TestContext context) throws UnsupportedEncodingException {
        context.assertEquals(200,
                get(context, "/profiles/" + profile.getId(),
                        token(admin.getEmail())));
    }

    @Test
    public void unauthorized(TestContext context) throws UnsupportedEncodingException {
        context.assertEquals(403,
                get(context, "/profiles/" + profile.getId() + 1,
                        token("allan@irunninglog.com")));
    }

    @Test
    public void badToken(TestContext context) {
        context.assertEquals(401,
                get(context, "/profiles/" + profile.getId(),
                        "foo"));
    }

    @Test
    public void wrongUser(TestContext context) throws UnsupportedEncodingException {
        context.assertEquals(401,
                get(context, "/profiles/" + profile.getId(),
                        token("allann@irunninglog.com")));
    }

    @Test
    public void notFound(TestContext context) throws UnsupportedEncodingException {
        context.assertEquals(404,
                get(context, "/profiles/" + profile.getId() + admin.getId(),
                        token(admin.getEmail())));
    }

}
