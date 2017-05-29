package com.irunninglog.main.identity;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.identity.IIdentityService;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.main.AbstractTest;
import com.irunninglog.vertx.identity.IdentityVerticle;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;

public class IdentityTest extends AbstractTest {

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        IdentityVerticle verticle = new IdentityVerticle(applicationContext.getBean(IFactory.class),
                applicationContext.getBean(IMapper.class),
                applicationContext.getBean(IIdentityService.class));

        return Collections.singletonList(verticle);
    }

    @Override
    protected void afterBefore(TestContext context) throws Exception {

    }

    @Test
    public void okExisting(TestContext context) throws UnsupportedEncodingException {
        context.assertEquals(401, post(context, "/identity", ""));
    }

    @Test
    public void okNew(TestContext context) throws UnsupportedEncodingException {
        context.assertEquals(401, post(context, "/identity", ""));
    }

}
