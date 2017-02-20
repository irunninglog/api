package com.irunninglog.vertx.route;

import com.irunninglog.api.security.ILoginService;
import com.irunninglog.vertx.endpoint.security.LoginVerticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

public class LoginHandlerTest extends AbstractHandlerTest {

    private final ILoginService service = Mockito.mock(ILoginService.class);

    @Override
    protected void afterBefore(TestContext context) {
        LoginVerticle verticle = new LoginVerticle(factory, mapper, service);
        vertx.deployVerticle(verticle, context.asyncAssertSuccess(s -> logger.info("Profile verticle deployed {}", s)));
    }

    @Test
    public void login(TestContext context) {
        context.assertEquals(200, post(context, "/authn", TOKEN));
    }

}
