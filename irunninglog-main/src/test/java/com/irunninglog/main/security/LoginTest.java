package com.irunninglog.main.security;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.ILoginService;
import com.irunninglog.main.AbstractTest;
import com.irunninglog.vertx.endpoint.security.LoginVerticle;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.Collections;

public class LoginTest extends AbstractTest {

    private String basic;

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        return Collections.singletonList(new LoginVerticle(applicationContext.getBean(IFactory.class),
                applicationContext.getBean(IMapper.class),
                applicationContext.getBean(ILoginService.class)));
    }

    @Override
    protected void afterBefore(TestContext context) throws Exception {
        save("login@irunninglog.com", "password");
        basic = token("login@irunninglog.com");
    }

    @Test
    public void basic(TestContext context) {
        context.assertEquals(200, post(context, "/authn", basic));
    }

}
