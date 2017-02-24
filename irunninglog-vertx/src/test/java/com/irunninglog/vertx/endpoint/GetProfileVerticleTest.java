package com.irunninglog.vertx.endpoint;

import com.irunninglog.api.*;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.vertx.mock.MockGetProfileRequest;
import com.irunninglog.vertx.mock.MockProfile;
import com.irunninglog.vertx.endpoint.profile.GetProfileVerticle;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.TestContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class GetProfileVerticleTest extends AbstractVerticleTest {

    private IProfileService profileService;
    private String profileVerticleId;

    @Before
    public void before(TestContext context) {
        profileService = Mockito.mock(IProfileService.class);

        GetProfileVerticle getProfileVerticle = new GetProfileVerticle(factory, mapper, profileService);
        rule.vertx().deployVerticle(getProfileVerticle, context.asyncAssertSuccess(event -> profileVerticleId = event));
    }

    @Test
    public void ok(TestContext context) {
        Mockito.when(profileService.get(any(Long.class))).thenReturn(new MockProfile());

        rule.vertx().eventBus().<String>send(Endpoint.GetProfile.getAddress(),
                Json.encode(new MockGetProfileRequest()), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IGetProfileResponse response = mapper.decode(s, IGetProfileResponse.class);

            context.assertEquals(ResponseStatus.Ok, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

    @Test
    public void statusException(TestContext context) {
        Mockito.when(profileService.get(any(Long.class))).thenThrow(new ResponseStatusException(ResponseStatus.NotFound));

        rule.vertx().eventBus().<String>send(Endpoint.GetProfile.getAddress(),
                Json.encode(new MockGetProfileRequest()), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IGetProfileResponse response = mapper.decode(s, IGetProfileResponse.class);

            context.assertEquals(ResponseStatus.NotFound, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

    @Test
    public void error1(TestContext context) {
        Mockito.when(profileService.get(any(Long.class))).thenThrow(new RuntimeException());

        rule.vertx().eventBus().<String>send(Endpoint.GetProfile.getAddress(),
                Json.encode(new MockGetProfileRequest()), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IGetProfileResponse response = mapper.decode(s, IGetProfileResponse.class);

            context.assertEquals(ResponseStatus.Error, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

    @Test
    public void error2(TestContext context) {
        Mockito.when(profileService.get(any(Long.class))).thenThrow(new RuntimeException());

        rule.vertx().undeploy(profileVerticleId, context.asyncAssertSuccess());
        IFactory throwsFactory = Mockito.mock(IFactory.class);
        //noinspection unchecked
        Mockito.when(throwsFactory.get(any(Class.class))).thenThrow(new IllegalArgumentException());
        rule.vertx().deployVerticle(new GetProfileVerticle(throwsFactory, mapper, profileService));

        rule.vertx().eventBus().<String>send(Endpoint.GetProfile.getAddress(),
                Json.encode(new MockGetProfileRequest()), context.asyncAssertSuccess(o -> context.assertNull(o.body())));
    }

}
