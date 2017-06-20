package com.irunninglog.main;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.spring.SpringConfig;
import com.irunninglog.strava.impl.StravaConfig;
import com.irunninglog.vertx.ServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RunWith(VertxUnitRunner.class)
public abstract class AbstractTest {

    private Vertx vertx;

    @Before
    public final void before(TestContext context) throws Exception {
        System.setProperty("strava", "1|foo");

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class, StravaConfig.class);

        vertx = Vertx.vertx();

        ServerVerticle verticle = new ServerVerticle(8889,
                context.asyncAssertSuccess(),
                applicationContext.getBean(IFactory.class),
                applicationContext.getBean(IMapper.class),
                applicationContext.getBean(IAuthenticationService.class));

        vertx.deployVerticle(verticle, context.asyncAssertSuccess());

        new RunningLogApplication().start(vertx, context.asyncAssertSuccess());
    }

    @After
    public final void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    protected int get(TestContext context, String path) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.get(8889, "localhost", path);

        final int[] responseCode = new int[1];
        req.handler(resp -> {
            responseCode[0] = resp.statusCode();
            async.complete();
        });
        req.end();

        async.awaitSuccess(5000);

        return responseCode[0];
    }

    int put(TestContext context, String path, String token, String body) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.put(8889, "localhost", path);
        if (token != null && !token.trim().isEmpty()) {
            req.putHeader("Authorization", token);
        }

        final int[] responseCode = new int[1];
        req.handler(resp -> {
            responseCode[0] = resp.statusCode();
            async.complete();
        });
        req.end(body);

        async.awaitSuccess(5000);

        return responseCode[0];
    }

}
