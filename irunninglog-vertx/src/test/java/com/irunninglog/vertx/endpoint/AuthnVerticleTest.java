package com.irunninglog.vertx.endpoint;

import com.irunninglog.security.*;
import com.irunninglog.service.Endpoint;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.vertx.security.AuthnVerticle;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.TestContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class AuthnVerticleTest extends AbstractVerticleTest {

    private final IAuthenticationService authenticationService = Mockito.mock(IAuthenticationService.class);

    private final AuthnRequest goodRequest = new AuthnRequest().setUsername("good").setPassword("pass").setEndpoint(Endpoint.GetProfile);
    private final User goodUser = new User();
    private final AuthnRequest authn = new AuthnRequest().setUsername("authn").setPassword("pass").setEndpoint(Endpoint.GetProfile);
    private final AuthnRequest authz = new AuthnRequest().setUsername("authz").setPassword("pass").setEndpoint(Endpoint.GetProfile);

    @Before
    public void before(TestContext context) throws AuthnException, AuthzException {
        AuthnVerticle authnVerticle = new AuthnVerticle(authenticationService);
        rule.vertx().deployVerticle(authnVerticle, context.asyncAssertSuccess());
    }

    @Test
    public void good(TestContext context) throws AuthnException, AuthzException {
        Mockito.when(authenticationService.authenticate(any(AuthnRequest.class))).thenReturn(goodUser);

        rule.vertx().eventBus().<String>send(AuthnVerticle.ADDRESS, Json.encode(goodRequest), context.asyncAssertSuccess(o -> {
            String s = o.body();
            AuthnResponse response = Json.decodeValue(s, AuthnResponse.class);

            context.assertEquals(ResponseStatus.Ok, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

    @Test
    public void authn(TestContext context) throws AuthnException, AuthzException {
        Mockito.when(authenticationService.authenticate(any(AuthnRequest.class))).thenThrow(new AuthnException(""));

        rule.vertx().eventBus().<String>send(AuthnVerticle.ADDRESS, Json.encode(authn), context.asyncAssertSuccess(o -> {
            String s = o.body();
            AuthnResponse response = Json.decodeValue(s, AuthnResponse.class);

            context.assertEquals(ResponseStatus.Unauthenticated, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

    @Test
    public void authz(TestContext context) throws AuthnException, AuthzException {
        Mockito.when(authenticationService.authenticate(any(AuthnRequest.class))).thenThrow(new AuthzException(""));

        rule.vertx().eventBus().<String>send(AuthnVerticle.ADDRESS, Json.encode(authz), context.asyncAssertSuccess(o -> {
            String s = o.body();
            AuthnResponse response = Json.decodeValue(s, AuthnResponse.class);

            context.assertEquals(ResponseStatus.Unauthorized, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

}
