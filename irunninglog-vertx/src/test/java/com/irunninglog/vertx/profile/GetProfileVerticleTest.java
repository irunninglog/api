package com.irunninglog.vertx.profile;

import com.irunninglog.api.*;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.security.IUser;
import com.irunninglog.vertx.AbstractVerticleTest;
import com.irunninglog.vertx.Envelope;
import io.vertx.ext.unit.TestContext;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

@Ignore
public class GetProfileVerticleTest extends AbstractVerticleTest {

    private IProfileService profileService;
    private String profileVerticleId;

    @Override
    protected void afterBefore(TestContext context) {
        profileService = Mockito.mock(IProfileService.class);

        GetProfileVerticle getProfileVerticle = new GetProfileVerticle(factory, mapper, profileService);
        rule.vertx().deployVerticle(getProfileVerticle, context.asyncAssertSuccess(event -> profileVerticleId = event));
    }

    @Test
    public void ok(TestContext context) {
        Mockito.when(profileService.get(any(String.class))).thenReturn(factory.get(IProfile.class).setId(1));

        Envelope envelope = new Envelope().setUser(factory.get(IUser.class).setId(1)).setRequest(mapper.encode(factory.get(IRequest.class)));

        rule.vertx().eventBus().<String>send(Endpoint.GET_PROFILE.getAddress(),
                mapper.encode(envelope), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IResponse response = mapper.decode(s, IResponse.class);

            context.assertEquals(ResponseStatus.OK, response.getStatus());
            context.assertNotNull(response.getBody());
        }));
    }

    @Test
    public void statusException(TestContext context) {
        Mockito.when(profileService.get(any(String.class))).thenThrow(new ResponseStatusException(ResponseStatus.NOT_FOUND));

        Envelope envelope = new Envelope().setUser(factory.get(IUser.class).setId(1)).setRequest(mapper.encode(factory.get(IRequest.class)));

        rule.vertx().eventBus().<String>send(Endpoint.GET_PROFILE.getAddress(),
                mapper.encode(envelope), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IResponse response = mapper.decode(s, IResponse.class);

            context.assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

    @Test
    public void error1(TestContext context) {
        Mockito.when(profileService.get(any(String.class))).thenThrow(new RuntimeException());

        rule.vertx().eventBus().<String>send(Endpoint.GET_PROFILE.getAddress(),
                mapper.encode(factory.get(IRequest.class)), context.asyncAssertSuccess(o -> {
            String s = o.body();
            IResponse response = mapper.decode(s, IResponse.class);

            context.assertEquals(ResponseStatus.ERROR, response.getStatus());
            context.assertNull(response.getBody());
        }));
    }

    @Test
    public void error2(TestContext context) {
        Mockito.when(profileService.get(any(String.class))).thenThrow(new RuntimeException());

        rule.vertx().undeploy(profileVerticleId, context.asyncAssertSuccess());
        IFactory throwsFactory = Mockito.mock(IFactory.class);
        //noinspection unchecked
        Mockito.when(throwsFactory.get(any(Class.class))).thenThrow(new IllegalArgumentException());
        rule.vertx().deployVerticle(new GetProfileVerticle(throwsFactory, mapper, profileService), event -> rule.vertx().eventBus().<String>send(Endpoint.GET_PROFILE.getAddress(),
                mapper.encode(factory.get(IRequest.class)), context.asyncAssertSuccess(o -> context.assertNull(o.body()))));
    }

}
