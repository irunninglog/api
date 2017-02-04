package com.irunninglog.vertx.endpoint;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthnRequest;
import com.irunninglog.api.security.IAuthnResponse;
import com.irunninglog.api.security.AuthzException;
import com.irunninglog.api.security.*;
import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.vertx.security.AuthnVerticle;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.TestContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class AuthnVerticleTest extends AbstractVerticleTest {

    private final IAuthenticationService authenticationService = Mockito.mock(IAuthenticationService.class);

    private final IAuthnRequest goodRequest = new IAuthnRequest().setToken("foo").setEndpoint(Endpoint.GetProfile);
    private final User goodUser = new User();
    private final IAuthnRequest authn = new IAuthnRequest().setToken("foo").setEndpoint(Endpoint.GetProfile);
    private final IAuthnRequest authz = new IAuthnRequest().setToken("foo").setEndpoint(Endpoint.GetProfile);

    @Before
    public void before(TestContext context) throws AuthnException, AuthzException {
        AuthnVerticle authnVerticle = new AuthnVerticle(authenticationService);
        rule.vertx().deployVerticle(authnVerticle, context.asyncAssertSuccess());
    }

    @Test
    public void good(TestContext context) throws AuthnException, AuthzException {
        Mockito.when(authenticationService.authenticate(any(IAuthnRequest.class))).thenReturn(goodUser);

        rule.vertx().eventBus().<String>send(AuthnVerticle.ADDRESS, Json.encode(goodRequest), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IAuthnResponse response = Json.decodeValue(s, IAuthnResponse.class);

            context.assertEquals(ResponseStatus.Ok, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

    @Test
    public void authn(TestContext context) throws AuthnException, AuthzException {
        Mockito.when(authenticationService.authenticate(any(IAuthnRequest.class))).thenThrow(new AuthnException(""));

        rule.vertx().eventBus().<String>send(AuthnVerticle.ADDRESS, Json.encode(authn), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IAuthnResponse response = Json.decodeValue(s, IAuthnResponse.class);

            context.assertEquals(ResponseStatus.Unauthenticated, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

    @Test
    public void authz(TestContext context) throws AuthnException, AuthzException {
        Mockito.when(authenticationService.authenticate(any(IAuthnRequest.class))).thenThrow(new AuthzException(""));

        rule.vertx().eventBus().<String>send(AuthnVerticle.ADDRESS, Json.encode(authz), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IAuthnResponse response = Json.decodeValue(s, IAuthnResponse.class);

            context.assertEquals(ResponseStatus.Unauthorized, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

}
