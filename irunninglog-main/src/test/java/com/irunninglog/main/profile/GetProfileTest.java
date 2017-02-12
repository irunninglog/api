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

import java.util.Collection;
import java.util.Collections;

public class GetProfileTest extends AbstractTest {

    private ProfileEntity profile;

    @Override
    protected void afterBefore(TestContext context) {
        profile = save("allan@irunninglog.com", "password", "MYPROFILE");
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
    public void ok(TestContext context) {
        context.assertEquals(200,
                request(context, "/profiles/" + profile.getId(),
                        token("allan@irunninglog.com", "password")));
    }

}
