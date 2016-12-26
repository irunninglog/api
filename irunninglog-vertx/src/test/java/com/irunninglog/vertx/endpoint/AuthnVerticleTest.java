package com.irunninglog.vertx.endpoint;

import com.irunninglog.security.*;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.vertx.Address;
import com.irunninglog.vertx.endpoint.authn.AuthnVerticle;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.TestContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AuthnVerticleTest extends AbstractVerticleTest {

    private final IAuthenticationService authenticationService = Mockito.mock(IAuthenticationService.class);
    private final IAuthorizationService authorizationService = Mockito.mock(IAuthorizationService.class);

    private final AuthnRequest goodRequest = new AuthnRequest().setUsername("good").setPassword("pass").setPath("path");
    private final User goodUser = new User();
    private final AuthnRequest authn = new AuthnRequest().setUsername("authn").setPassword("pass");
    private final AuthnRequest authz = new AuthnRequest().setUsername("authz").setPassword("pass").setPath("path");
    private final User authzUser = new User();

    @Before
    public void before(TestContext context) throws AuthnException, AuthzException {
        Mockito.when(authenticationService.authenticate("good", "pass")).thenReturn(goodUser);
        Mockito.when(authenticationService.authenticate("authn", "pass")).thenThrow(new AuthnException(""));
        Mockito.when(authenticationService.authenticate("authz", "pass")).thenReturn(authzUser);

        Mockito.when(authorizationService.authorize(goodUser, "path")).thenReturn(goodUser);
        Mockito.when(authorizationService.authorize(authzUser, "path")).thenThrow(new AuthzException(""));

        AuthnVerticle authnVerticle = new AuthnVerticle(authenticationService, authorizationService);
        rule.vertx().deployVerticle(authnVerticle, context.asyncAssertSuccess());
    }

    @Test
    public void good(TestContext context) {
        rule.vertx().eventBus().<String>send(Address.Authenticate.getAddress(), Json.encode(goodRequest), context.asyncAssertSuccess(o -> {
            String s = o.body();
            AuthnResponse response = Json.decodeValue(s, AuthnResponse.class);

            context.assertEquals(ResponseStatus.Ok, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

    @Test
    public void authn(TestContext context) {
        rule.vertx().eventBus().<String>send(Address.Authenticate.getAddress(), Json.encode(authn), context.asyncAssertSuccess(o -> {
            String s = o.body();
            AuthnResponse response = Json.decodeValue(s, AuthnResponse.class);

            context.assertEquals(ResponseStatus.Unauthenticated, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

    @Test
    public void authz(TestContext context) {
        rule.vertx().eventBus().<String>send(Address.Authenticate.getAddress(), Json.encode(authz), context.asyncAssertSuccess(o -> {
            String s = o.body();
            AuthnResponse response = Json.decodeValue(s, AuthnResponse.class);

            context.assertEquals(ResponseStatus.Unauthorized, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

}
