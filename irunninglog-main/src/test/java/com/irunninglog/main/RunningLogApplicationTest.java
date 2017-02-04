package com.irunninglog.main;

import com.irunninglog.dashboard.DashboardRequest;
import com.irunninglog.dashboard.DashboardResponse;
import com.irunninglog.profile.ProfileRequest;
import com.irunninglog.profile.ProfileResponse;
import com.irunninglog.api.security.IAuthnRequest;
import com.irunninglog.api.security.IAuthnResponse;
import com.irunninglog.api.Endpoint;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.vertx.http.ServerVerticle;
import com.irunninglog.vertx.security.AuthnVerticle;
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

        ServerVerticle verticle = new ServerVerticle(8889, context.asyncAssertSuccess());
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
        vertx.eventBus().<String>send(AuthnVerticle.ADDRESS,
                Json.encode(new IAuthnRequest()),
                messageAsyncResult -> {
                    context.assertTrue(messageAsyncResult.succeeded());
                    IAuthnResponse response = Json.decodeValue(messageAsyncResult.result().body(), IAuthnResponse.class);
                    context.assertEquals(ResponseStatus.Unauthenticated, response.getStatus());
                    async.complete();
                });

        async.awaitSuccess(60000);
    }

    @Test
    public void profileGet(TestContext context) {
        Async async = context.async();
        vertx.eventBus().<String>send(Endpoint.GetProfile.getAddress(),
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
        vertx.eventBus().<String>send(Endpoint.GetDashboard.getAddress(),
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
