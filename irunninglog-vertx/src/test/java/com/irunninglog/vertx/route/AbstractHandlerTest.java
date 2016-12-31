package com.irunninglog.vertx.route;

import com.irunninglog.security.*;
import com.irunninglog.vertx.endpoint.authn.AuthnVerticle;
import com.irunninglog.vertx.http.ServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

@RunWith(VertxUnitRunner.class)
public abstract class AbstractHandlerTest {

    final static String TOKEN = "Basic dXNlcm5hbWU6cGFzc3dvcmQ=";

    final IAuthenticationService authenticationService = Mockito.mock(IAuthenticationService.class);
    private final IAuthorizationService authorizationService = Mockito.mock(IAuthorizationService.class);

    Vertx vertx;
    String authVerticleId;

    @Before
    public final void before(TestContext context) {
        vertx = Vertx.vertx();

        ServerVerticle verticle = new ServerVerticle(8889);
        vertx.deployVerticle(verticle, context.asyncAssertSuccess());

        AuthnVerticle authnVerticle = new AuthnVerticle(authenticationService, authorizationService);
        vertx.deployVerticle(authnVerticle, context.asyncAssertSuccess(s -> authVerticleId = s));

        afterBefore(context);
    }

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    protected abstract void afterBefore(TestContext context);

    final void authn() throws AuthnException, AuthzException {
        User user = new User();
        Mockito.when(authenticationService.authenticate(any(String.class), any(String.class))).thenReturn(user);
        Mockito.when(authorizationService.authorize(any(User.class), any(String.class))).thenReturn(user);
    }

    final int request(TestContext context, String path, String token) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.get(8889, "localhost", path);
        req.putHeader("Authorization", token);

        final int[] responseCode = new int[1];
        req.handler(resp -> {
            responseCode[0] = resp.statusCode();
            async.complete();
        });
        req.end();

        async.awaitSuccess(60000);

        return responseCode[0];
    }

}
