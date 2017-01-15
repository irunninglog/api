package com.irunninglog.vertx.route;

import com.irunninglog.security.*;
import com.irunninglog.vertx.security.AuthnVerticle;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Matchers.any;

@RunWith(VertxUnitRunner.class)
public abstract class AbstractHandlerTest {

    final static String TOKEN = "Basic dXNlcm5hbWU6cGFzc3dvcmQ=";

    final Logger logger = LoggerFactory.getLogger(getClass());
    final IAuthenticationService authenticationService = Mockito.mock(IAuthenticationService.class);

    Vertx vertx;
    String authVerticleId;

    @Before
    public final void before(TestContext context) {
        vertx = Vertx.vertx();

        ServerVerticle verticle = new ServerVerticle(8889);
        vertx.deployVerticle(verticle, context.asyncAssertSuccess(s -> logger.info("Server verticle deployed {}", s)));

        AuthnVerticle authnVerticle = new AuthnVerticle(authenticationService);
        vertx.deployVerticle(authnVerticle, context.asyncAssertSuccess(s -> {
            logger.info("Auth verticle deployed {}", s);

            authVerticleId = s;
        }));

        afterBefore(context);
    }

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    protected abstract void afterBefore(TestContext context);

    final void authn() throws AuthnException, AuthzException {
        User user = new User();
        Mockito.when(authenticationService.authenticate(any(AuthnRequest.class))).thenReturn(user);
    }

    final int request(TestContext context, String path, String token) {
        logger.info("Sending request {}", path);

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
