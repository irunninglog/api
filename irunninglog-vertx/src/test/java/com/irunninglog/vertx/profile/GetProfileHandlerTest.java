package com.irunninglog.vertx.profile;

import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ResponseStatusException;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.vertx.AbstractHandlerTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

@Ignore
public class GetProfileHandlerTest extends AbstractHandlerTest {

    private final IProfileService profileService = Mockito.mock(IProfileService.class);

    private String profileVerticleId;

    @Override
    protected void afterBefore(TestContext context) {
        GetProfileVerticle getProfileVerticle = new GetProfileVerticle(factory, mapper, profileService);
        vertx.deployVerticle(getProfileVerticle, context.asyncAssertSuccess(s -> profileVerticleId = s));
    }

    @Test
    public void ok(TestContext context) throws AuthnException {
        authn();

        Mockito.when(profileService.get(any(String.class)))
                .thenReturn(factory.get(IProfile.class));

        context.assertEquals(403, get(context, "/profiles/1", TOKEN));
    }

    @Test
    public void notFound(TestContext context) throws AuthnException {
        authn();

        Mockito.when(profileService.get(any(String.class)))
                .thenThrow(new ResponseStatusException(ResponseStatus.NOT_FOUND));

        context.assertEquals(403, get(context, "/profiles/1", TOKEN));
    }

    @Test
    public void unauthenticated1(TestContext context) throws AuthnException {
        Mockito.when(authenticationService.authenticate(any(String.class))).thenThrow(new AuthnException("UNAUTHENTICATED"));

        context.assertEquals(401, get(context, "/profiles/1", ""));
    }

    @Test
    public void unauthenticated2(TestContext context) throws AuthnException {
        Mockito.when(authenticationService.authenticate(any(String.class))).thenThrow(new AuthnException("UNAUTHENTICATED"));

        context.assertEquals(401, get(context, "/profiles/1", TOKEN));
    }

    @Test
    public void unauthenticated3(TestContext context) throws AuthnException {
        Mockito.when(authenticationService.authenticate(any(String.class))).thenThrow(new AuthnException("UNAUTHENTICATED"));

        context.assertEquals(401, get(context, "/profiles/1", "Basic @@@"));
    }

    @Test
    public void error1(TestContext context) throws AuthnException {
        authn();

        vertx.undeploy(profileVerticleId);
        context.assertEquals(403, get(context, "/profiles/1", TOKEN));
    }

    @Test
    public void error2(TestContext context) throws AuthnException {
        authn();

        vertx.undeploy(profileVerticleId);
        IMapper throwsMapper = Mockito.mock(IMapper.class);
        //noinspection unchecked
        Mockito.when(throwsMapper.decode(any(String.class), any(Class.class))).thenThrow(new IllegalArgumentException());
        vertx.deployVerticle(new GetProfileVerticle(factory, throwsMapper, profileService), context.asyncAssertSuccess());

        context.assertEquals(403, get(context, "/profiles/1", TOKEN));
    }

}
