package com.irunninglog.vertx.endpoint;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.security.*;
import com.irunninglog.api.security.SecurityException;
import com.irunninglog.vertx.mock.MockAuthnRequest;
import com.irunninglog.vertx.mock.MockUser;
import com.irunninglog.vertx.security.AuthnVerticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class AuthnVerticleTest extends AbstractVerticleTest {

    private final IAuthenticationService authenticationService = Mockito.mock(IAuthenticationService.class);

    private final IAuthnRequest goodRequest = new MockAuthnRequest().setToken("foo").setEndpoint(Endpoint.PROFILE_GET);
    private final IUser goodUser = new MockUser();
    private final IAuthnRequest authn = new MockAuthnRequest().setToken("foo").setEndpoint(Endpoint.PROFILE_GET);
    private final IAuthnRequest authz = new MockAuthnRequest().setToken("foo").setEndpoint(Endpoint.PROFILE_GET);

    @Before
    public void before(TestContext context) throws AuthnException, AuthzException {
        AuthnVerticle authnVerticle = new AuthnVerticle(authenticationService, factory, mapper);
        rule.vertx().deployVerticle(authnVerticle, context.asyncAssertSuccess());
    }

    @Test
    public void good(TestContext context) throws SecurityException {
        Mockito.when(authenticationService.authenticate(any(Endpoint.class), any(String.class), any(String.class))).thenReturn(goodUser);

        rule.vertx().eventBus().<String>send(AuthnVerticle.ADDRESS, mapper.encode(goodRequest), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IAuthnResponse response = mapper.decode(s, IAuthnResponse.class);

            context.assertEquals(ResponseStatus.OK, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

    @Test
    public void authn(TestContext context) throws SecurityException {
        Mockito.when(authenticationService.authenticate(any(Endpoint.class), any(String.class), any(String.class))).thenThrow(new AuthnException(""));

        rule.vertx().eventBus().<String>send(AuthnVerticle.ADDRESS, mapper.encode(authn), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IAuthnResponse response = mapper.decode(s, IAuthnResponse.class);

            context.assertEquals(ResponseStatus.UNAUTHENTICATED, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

    @Test
    public void authz(TestContext context) throws SecurityException {
        Mockito.when(authenticationService.authenticate(any(Endpoint.class), any(String.class), any(String.class))).thenThrow(new AuthzException(""));

        rule.vertx().eventBus().<String>send(AuthnVerticle.ADDRESS, mapper.encode(authz), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IAuthnResponse response = mapper.decode(s, IAuthnResponse.class);

            context.assertEquals(ResponseStatus.UNAUTHORIZED, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

}
