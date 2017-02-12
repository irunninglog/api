package com.irunninglog.main.dashboard;

import com.irunninglog.api.dashboard.IDashboardService;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.main.AbstractTest;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.vertx.endpoint.dashboard.GetDashboardVerticle;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.Collections;

public class GetDashboardTest extends AbstractTest {

    private ProfileEntity profile;

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        GetDashboardVerticle verticle =
                new GetDashboardVerticle(applicationContext.getBean(IDashboardService.class),
                        applicationContext.getBean(IFactory.class),
                        applicationContext.getBean(IMapper.class));

        return Collections.singletonList(verticle);
    }

    @Override
    protected void afterBefore(TestContext context) {
        profile = save("dashboard@irunninglog.com", "password", "MYPROFILE");
    }

    @Test
    public void ok(TestContext context) {
        context.assertEquals(200, request(context, "/profiles/" +profile.getId() + "/dashboard", token("dashboard@irunninglog.com", "password")));
    }

}
