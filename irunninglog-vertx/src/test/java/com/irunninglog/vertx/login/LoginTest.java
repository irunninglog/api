package com.irunninglog.vertx.login;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.vertx.AbstractTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

public class LoginTest extends AbstractTest {

    @Override
    protected void afterBefore(TestContext context) {
        super.afterBefore(context);

        deploy(new LoginVerticle(factory(), mapper(), authenticationService()), context);
    }

    @Test
    public void loginSuccess(TestContext context) {
        assertEquals(200, get(context, "/api/login"));
    }

    @Test
    public void exceptionFromAuthService(TestContext context) throws AuthnException {
        Mockito.when(authenticationService().authenticateCode(any(String.class))).thenThrow(new AuthnException(""));

        assertEquals(401, get(context, "/api/login"));
    }

}
