package com.irunninglog.main.security;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.ILoginService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.main.AbstractTest;
import com.irunninglog.spring.profile.ProfileEntity;
import com.irunninglog.vertx.endpoint.security.LoginVerticle;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Base64;
import java.util.Collection;
import java.util.Collections;

public class LoginTest extends AbstractTest {

    private String basic;
    private String token;

    @Override
    protected Collection<Verticle> verticles(ApplicationContext applicationContext) {
        return Collections.singletonList(new LoginVerticle(applicationContext.getBean(IFactory.class),
                applicationContext.getBean(IMapper.class),
                applicationContext.getBean(ILoginService.class)));
    }

    @Override
    protected void afterBefore(TestContext context) throws Exception {
        ProfileEntity profileEntity = save("login@irunninglog.com", "password", "MYPROFILE");
        basic = token("login@irunninglog.com", "password");

        IAuthenticationService service = applicationContext.getBean(IAuthenticationService.class);
        token = "Token " + service.token(applicationContext.getBean(IUser.class).setUsername("login@irunninglog.com").setId(profileEntity.getId()));
    }

    @Test
    public void basic(TestContext context) {
        context.assertEquals(200, post(context, "/authn", basic));
    }

    @Test
    public void token(TestContext context) {
        context.assertEquals(200, post(context, "/authn", token));
    }

    @Test
    public void serverToken(TestContext context) {
        String serverToken = postAndGetToken(context, "/authn", token);
        context.assertNotNull(serverToken);
        context.assertTrue(new String(Base64.getDecoder().decode(serverToken)).contains("login@irunninglog.com"));
    }

}
