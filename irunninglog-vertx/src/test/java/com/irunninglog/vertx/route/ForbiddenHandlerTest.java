package com.irunninglog.vertx.route;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.AuthzException;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class ForbiddenHandlerTest extends AbstractHandlerTest {

    @Override
    protected void afterBefore(TestContext context) {

    }

    @Test
    public void forbidden(TestContext context) throws AuthnException, AuthzException {
        Mockito.when(authenticationService.authenticate(any(Endpoint.class), any(String.class), any(String.class))).thenThrow(new AuthzException(""));
        context.assertEquals(403, get(context, "/forbidden", TOKEN));
    }

}
