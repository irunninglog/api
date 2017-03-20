package com.irunninglog.vertx.route;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.SecurityException;
import com.irunninglog.vertx.http.ServerVerticle;
import com.irunninglog.vertx.mock.MockFactory;
import com.irunninglog.vertx.mock.MockMapper;
import com.irunninglog.vertx.mock.MockUser;
import com.irunninglog.vertx.security.AuthnVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
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
    final IMapper mapper = new MockMapper();
    final IFactory factory = new MockFactory();

    Vertx vertx;
    String authVerticleId;

    @Before
    public final void before(TestContext context) {
        logger.info("Before {}", context);

        vertx = Vertx.vertx();

        Async async = context.async();

        ServerVerticle verticle = new ServerVerticle(8889, context.asyncAssertSuccess(event -> async.complete()), factory, mapper);
        vertx.deployVerticle(verticle, context.asyncAssertSuccess(s -> logger.info("Server verticle deployed {}", s)));

        AuthnVerticle authnVerticle = new AuthnVerticle(authenticationService, factory, mapper);
        vertx.deployVerticle(authnVerticle, context.asyncAssertSuccess(s -> {
            logger.info("Auth verticle deployed {}", s);

            authVerticleId = s;
        }));

        logger.info("Before afterBefore {}", context);
        afterBefore(context);
        logger.info("After afterBefore {}", context);

        async.awaitSuccess(10000);
    }

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    protected abstract void afterBefore(TestContext context);

    final void authn() throws SecurityException {
        Mockito.when(authenticationService.authenticate(any(Endpoint.class), any(String.class), any(String.class)))
                .thenReturn(new MockUser());
    }

    final int get(TestContext context, String path, String token) {
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

        async.awaitSuccess(10000);

        return responseCode[0];
    }

    final int post(TestContext context, String path, String token) {
        logger.info("Sending request {}", path);

        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.post(8889, "localhost", path);
        req.putHeader("Authorization", token);

        final int[] responseCode = new int[1];
        req.handler(resp -> {
            responseCode[0] = resp.statusCode();
            async.complete();
        });
        req.end();

        async.awaitSuccess(10000);

        return responseCode[0];
    }

    int put(TestContext context, String path, String token, String body) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.put(8889, "localhost", path);
        req.putHeader("Authorization", token);

        final int[] responseCode = new int[1];
        req.handler(resp -> {
            responseCode[0] = resp.statusCode();
            async.complete();
        });
        req.end(Buffer.buffer(body));

        async.awaitSuccess(10000);

        return responseCode[0];
    }

    int delete(TestContext context, String path, String token) {
        logger.info("Sending request {}", path);

        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.delete(8889, "localhost", path);
        req.putHeader("Authorization", token);

        final int[] responseCode = new int[1];
        req.handler(resp -> {
            responseCode[0] = resp.statusCode();
            async.complete();
        });
        req.end();

        async.awaitSuccess(10000);

        return responseCode[0];
    }

}
