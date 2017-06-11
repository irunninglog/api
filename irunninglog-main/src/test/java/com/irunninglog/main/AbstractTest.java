package com.irunninglog.main;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IAuthenticationService;
import com.irunninglog.spring.context.ContextConfiguration;
import com.irunninglog.vertx.ServerVerticle;
import io.vertx.core.Verticle;
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

import java.time.ZonedDateTime;
import java.util.Collection;

@RunWith(VertxUnitRunner.class)
public abstract class AbstractTest {

    private final Integer offset = -1 * ZonedDateTime.now().getOffset().getTotalSeconds() / 60;

    private Vertx vertx;

    @Before
    public final void before(TestContext context) throws Exception {
        System.setProperty("dataSource", "org.h2.Driver|jdbc:h2:mem:test;DB_CLOSE_DELAY=-1|sa");
        System.setProperty("jpa", "update|org.hibernate.dialect.H2Dialect");
        System.setProperty("jwt", "issuer|secret");
        System.setProperty("strava", "1|foo");

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContextConfiguration.class);

        vertx = Vertx.vertx();

        Async async = context.async();

        IFactory factory = applicationContext.getBean(IFactory.class);
        IMapper mapper = applicationContext.getBean(IMapper.class);

        ServerVerticle serverVerticle = new ServerVerticle(8889,
                context.asyncAssertSuccess(event -> async.complete()),
                factory,
                mapper,
                applicationContext.getBean(IAuthenticationService.class));

        vertx.deployVerticle(serverVerticle, context.asyncAssertSuccess());

        for (Verticle verticle : verticles(applicationContext)) {
            vertx.deployVerticle(verticle, context.asyncAssertSuccess());
        }

        async.awaitSuccess(5000);

        afterBefore(context);
    }

    protected abstract Collection<Verticle> verticles(ApplicationContext applicationContext);

    protected abstract void afterBefore(TestContext context) throws Exception;

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    protected int get(TestContext context, String path, String token) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.get(8889, "localhost", path);
        if (token != null && !token.trim().isEmpty()) {
            req.putHeader("Authorization", token);
            req.putHeader("iRunningLog-Utc-Offset", offset.toString());
        }

        final int[] responseCode = new int[1];
        req.handler(resp -> {
            responseCode[0] = resp.statusCode();
            async.complete();
        });
        req.end();

        async.awaitSuccess(5000);

        return responseCode[0];
    }

    protected int post(TestContext context, String path, String token) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.post(8889, "localhost", path);
        if (token != null && !token.trim().isEmpty()) {
            req.putHeader("Authorization", token);
        }

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

    protected int delete(TestContext context, String path, String token) {
        HttpClient client = vertx.createHttpClient();
        Async async = context.async();
        HttpClientRequest req = client.delete(8889, "localhost", path);
        if (token != null && !token.trim().isEmpty()) {
            req.putHeader("Authorization", token);
            req.putHeader("iRunningLog-Utc-Offset", offset.toString());
        }

        final int[] responseCode = new int[1];
        req.handler(resp -> {
            responseCode[0] = resp.statusCode();
            async.complete();
        });
        req.end();

        async.awaitSuccess(5000);

        return responseCode[0];
    }

}
