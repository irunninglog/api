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

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

public class GetDashboardTest extends AbstractTest {

    private ProfileEntity profile;
    private ProfileEntity admin;

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
        admin = save("admin@irunninglog.com", "password", "ADMIN");
    }

    @Test
    public void ok(TestContext context) {
        saveWorkout(profile, LocalDate.now(), 10, 60 * 60 * 1000, null, null, null);
        saveWorkout(profile, LocalDate.now().minusDays(1), 10, 60 * 60 * 1000, null, null, null);
        saveWorkout(profile, LocalDate.now().minusMonths(1), 10, 60 * 60 * 1000, null, null, null);
        saveWorkout(profile, LocalDate.now().minusYears(1), 10, 60 * 60 * 1000, null, null, null);

        saveShoe(profile, "one", Boolean.TRUE);
        saveShoe(profile, "two", Boolean.TRUE, LocalDate.now());
        context.assertEquals(200, get(context, "/profiles/" +profile.getId() + "/dashboard", token("dashboard@irunninglog.com", "password")));
    }

    @Test
    public void notFound(TestContext context) {
        context.assertEquals(404, get(context, "/profiles/" + (profile.getId() + admin.getId()) + "/dashboard", token("admin@irunninglog.com", "password")));
    }

}
