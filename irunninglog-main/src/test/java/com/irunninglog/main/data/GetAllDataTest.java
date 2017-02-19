package com.irunninglog.main.data;

import com.irunninglog.api.data.IDataService;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.main.AbstractTest;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.vertx.endpoint.data.GetRoutesVerticle;
import com.irunninglog.vertx.endpoint.data.GetRunsVerticle;
import com.irunninglog.vertx.endpoint.data.GetShoesVerticle;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collection;

public class GetAllDataTest extends AbstractTest {

    private ProfileEntity profileEntity;

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        IFactory factory = applicationContext.getBean(IFactory.class);
        IMapper mapper = applicationContext.getBean(IMapper.class);
        IDataService dataService = applicationContext.getBean(IDataService.class);

        Collection<Verticle> verticles = new ArrayList<>();

        verticles.add(new GetShoesVerticle(dataService, factory, mapper));
        verticles.add(new GetRunsVerticle(dataService, factory, mapper));
        verticles.add(new GetRoutesVerticle(dataService, factory, mapper));

        return verticles;
    }

    @Override
    protected void afterBefore(TestContext context) {
        profileEntity = save("data@irunninglog.com", "password", "MYPROFILE");
    }

    @Test
    public void shoes(TestContext context) {
        context.assertEquals(200, get(context, "/profiles/" + profileEntity.getId() + "/shoes", token("data@irunninglog.com", "password")));
    }

    @Test
    public void routes(TestContext context) {
        context.assertEquals(200, get(context, "/profiles/" + profileEntity.getId() + "/routes", token("data@irunninglog.com", "password")));
    }

    @Test
    public void runs(TestContext context) {
        context.assertEquals(200, get(context, "/profiles/" + profileEntity.getId() + "/runs", token("data@irunninglog.com", "password")));
    }

}
