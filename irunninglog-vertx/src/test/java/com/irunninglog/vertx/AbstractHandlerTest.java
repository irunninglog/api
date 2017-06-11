package com.irunninglog.vertx;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.api.security.IUser;
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

import java.util.Collections;

import static org.mockito.Matchers.any;

@RunWith(VertxUnitRunner.class)
public abstract class AbstractHandlerTest {

    protected final static String TOKEN = "Basic dXNlcm5hbWU6cGFzc3dvcmQ=";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected final IAuthenticationService authenticationService = Mockito.mock(IAuthenticationService.class);
    protected IFactory factory;
    protected IMapper mapper;

    protected Vertx vertx;

    @Before
    public final void before(TestContext context) throws Exception {
        logger.info("Before {}", context);

        vertx = Vertx.vertx();

        Async async = context.async();

        ServerVerticle verticle = new ServerVerticle(8889, context.asyncAssertSuccess(event -> async.complete()), factory, mapper, authenticationService);
        vertx.deployVerticle(verticle, context.asyncAssertSuccess(s -> logger.info("Server verticle deployed {}", s)));

        logger.info("Before afterBefore {}", context);
        afterBefore(context);
        logger.info("After afterBefore {}", context);

        async.awaitSuccess(10000);
    }

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    protected abstract void afterBefore(TestContext context) throws Exception;

    protected final void authn() throws AuthnException {
        Mockito.when(authenticationService.authenticate(any(String.class)))
                .thenReturn(factory.get(IUser.class).setId(1).setAuthorities(Collections.singletonList("MYPROFILE")));
    }

    protected final int get(TestContext context, String path, String token) {
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

    protected final int post(TestContext context, String path, String token) {
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

    protected int delete(TestContext context, String path, String token) {
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
