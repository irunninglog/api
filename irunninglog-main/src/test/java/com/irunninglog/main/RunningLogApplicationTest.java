package com.irunninglog.main;

import com.irunninglog.dashboard.DashboardRequest;
import com.irunninglog.dashboard.DashboardResponse;
import com.irunninglog.profile.ProfileRequest;
import com.irunninglog.profile.ProfileResponse;
import com.irunninglog.security.AuthnRequest;
import com.irunninglog.security.AuthnResponse;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.vertx.Address;
import com.irunninglog.vertx.http.ServerVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@RunWith(VertxUnitRunner.class)
public class RunningLogApplicationTest {

    private Vertx vertx;

    @Before
    public final void before(TestContext context) throws IOException {
        System.setProperty("env", "file:///" + new ClassPathResource("application.properties").getFile().getAbsolutePath());

        vertx = Vertx.vertx();

        ServerVerticle verticle = new ServerVerticle(8889);
        vertx.deployVerticle(verticle, context.asyncAssertSuccess());

        new RunningLogApplication().start(vertx, context.asyncAssertSuccess());
    }

    @After
    public void after(TestContext context) {
        System.clearProperty("env");

        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void authn(TestContext context) {
        Async async = context.async();
        vertx.eventBus().<String>send(Address.Authenticate.getAddress(),
                Json.encode(new AuthnRequest()),
                messageAsyncResult -> {
                    context.assertTrue(messageAsyncResult.succeeded());
                    AuthnResponse response = Json.decodeValue(messageAsyncResult.result().body(), AuthnResponse.class);
                    context.assertEquals(ResponseStatus.Unauthenticated, response.getStatus());
                    async.complete();
                });

        async.awaitSuccess(60000);
    }

    @Test
    public void profileGet(TestContext context) {
        Async async = context.async();
        vertx.eventBus().<String>send(Address.ProfileGet.getAddress(),
                Json.encode(new ProfileRequest().setId(1).setOffset(300)),
                messageAsyncResult -> {
                    context.assertTrue(messageAsyncResult.succeeded());
                    ProfileResponse response = Json.decodeValue(messageAsyncResult.result().body(), ProfileResponse.class);
                    context.assertEquals(ResponseStatus.NotFound, response.getStatus());
                    async.complete();
                });

        async.awaitSuccess(60000);
    }

    @Test
    public void dashbaordGet(TestContext context) {
        Async async = context.async();
        vertx.eventBus().<String>send(Address.DashboardGet.getAddress(),
                Json.encode(new DashboardRequest().setId(1).setOffset(300)),
                messageAsyncResult -> {
                    context.assertTrue(messageAsyncResult.succeeded());
                    DashboardResponse response = Json.decodeValue(messageAsyncResult.result().body(), DashboardResponse.class);
                    context.assertEquals(ResponseStatus.NotFound, response.getStatus());
                    async.complete();
                });

        async.awaitSuccess(60000);
    }

}
