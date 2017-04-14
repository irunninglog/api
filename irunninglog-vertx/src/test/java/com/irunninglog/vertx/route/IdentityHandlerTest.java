package com.irunninglog.vertx.route;

import com.irunninglog.api.identity.IIdentityService;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.vertx.endpoint.identity.IdentityVerticle;
import com.irunninglog.vertx.mock.MockIdentity;
import com.irunninglog.vertx.mock.MockUser;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class IdentityHandlerTest extends AbstractHandlerTest {

    private IIdentityService service;

    @Override
    protected void afterBefore(TestContext context) throws AuthnException {
        service = Mockito.mock(IIdentityService.class);
        vertx.deployVerticle(new IdentityVerticle(factory, mapper, service));

        Mockito.when(authenticationService.authenticate(any(String.class))).thenReturn(new MockUser());
    }

    @Test
    public void getNewIdentity(TestContext context) {
        Mockito.when(service.identity(any(String.class))).thenReturn(new MockIdentity().setCreated(Boolean.TRUE).setUsername("foo").setId(1));
        context.assertEquals(201, post(context, "/identity", TOKEN));
    }

    @Test
    public void getExistingIdentity(TestContext context) {
        Mockito.when(service.identity(any(String.class))).thenReturn(new MockIdentity().setCreated(Boolean.FALSE).setUsername("foo").setId(1));
        context.assertEquals(200, post(context, "/identity", TOKEN));
    }

}
