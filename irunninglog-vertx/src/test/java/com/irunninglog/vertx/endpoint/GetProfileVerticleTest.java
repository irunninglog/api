package com.irunninglog.vertx.endpoint;

import com.irunninglog.api.*;
import com.irunninglog.api.profile.IGetProfileRequest;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.vertx.endpoint.profile.GetProfileVerticle;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.TestContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class GetProfileVerticleTest extends AbstractVerticleTest {

    private IProfileService profileService;

    @Before
    public void before(TestContext context) {
        profileService = Mockito.mock(IProfileService.class);

        GetProfileVerticle getProfileVerticle = new GetProfileVerticle(Mockito.mock(IFactory.class), profileService);
        rule.vertx().deployVerticle(getProfileVerticle, context.asyncAssertSuccess());
    }

    @Test
    public void ok(TestContext context) {
        Mockito.when(profileService.get(any(Long.class))).thenReturn(Mockito.mock(IProfile.class));

        rule.vertx().eventBus().<String>send(Endpoint.GetProfile.getAddress(), Json.encode(Mockito.mock(IGetProfileRequest.class)), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IGetProfileResponse response = Json.decodeValue(s, IGetProfileResponse.class);

            context.assertEquals(ResponseStatus.Ok, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

    @Test
    public void statusException(TestContext context) {
        Mockito.when(profileService.get(any(Long.class))).thenThrow(new ResponseStatusException(ResponseStatus.NotFound));

        rule.vertx().eventBus().<String>send(Endpoint.GetProfile.getAddress(), Json.encode(Mockito.mock(IGetProfileRequest.class)), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IGetProfileResponse response = Json.decodeValue(s, IGetProfileResponse.class);

            context.assertEquals(ResponseStatus.NotFound, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

    @Test
    public void error(TestContext context) {
        Mockito.when(profileService.get(any(Long.class))).thenThrow(new RuntimeException());

        rule.vertx().eventBus().<String>send(Endpoint.GetProfile.getAddress(), Json.encode(Mockito.mock(IGetProfileRequest.class)), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IGetProfileResponse response = Json.decodeValue(s, IGetProfileResponse.class);

            context.assertEquals(ResponseStatus.Error, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

}
