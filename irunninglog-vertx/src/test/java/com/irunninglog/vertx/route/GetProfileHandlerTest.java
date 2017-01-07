package com.irunninglog.vertx.route;

import com.irunninglog.profile.IProfileService;
import com.irunninglog.profile.Profile;
import com.irunninglog.profile.ProfileRequest;
import com.irunninglog.profile.ProfileResponse;
import com.irunninglog.security.*;
import com.irunninglog.service.ResponseStatus;
import com.irunninglog.service.ResponseStatusException;
import com.irunninglog.vertx.endpoint.profile.GetProfileVerticle;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class GetProfileHandlerTest extends AbstractHandlerTest {

    private final IProfileService profileService = Mockito.mock(IProfileService.class);

    private String profileVerticleId;

    @Override
    protected void afterBefore(TestContext context) {
        GetProfileVerticle getProfileVerticle = new GetProfileVerticle(profileService);
        vertx.deployVerticle(getProfileVerticle, context.asyncAssertSuccess(s -> profileVerticleId = s));
    }

    @Test
    public void ok(TestContext context) throws AuthnException, AuthzException {
        authn();

        Mockito.when(profileService.get(any(ProfileRequest.class)))
                .thenReturn(new ProfileResponse().setBody(new Profile()).setStatus(ResponseStatus.Ok));

        context.assertEquals(200, request(context, "/profiles/1", TOKEN));
    }

    @Test
    public void notFound(TestContext context) throws AuthnException, AuthzException {
        authn();

        Mockito.when(profileService.get(any(ProfileRequest.class)))
                .thenThrow(new ResponseStatusException(ResponseStatus.NotFound));

        context.assertEquals(404, request(context, "/profiles/1", TOKEN));
    }

    @Test
    public void unauthenticated1(TestContext context) throws AuthnException, AuthzException {
        authn();

        context.assertEquals(401, request(context, "/profiles/1", ""));
    }

    @Test
    public void unauthenticated2(TestContext context) throws AuthnException {
        Mockito.when(authenticationService.authenticate(any(String.class), any(String.class))).thenThrow(new AuthnException("Unauthenticated"));

        context.assertEquals(401, request(context, "/profiles/1", TOKEN));
    }

    @Test
    public void unauthenticated3(TestContext context) throws AuthnException, AuthzException {
        authn();

        context.assertEquals(401, request(context, "/profiles/1", "Basic @@@"));
    }

    @Test
    public void error1(TestContext context) throws AuthnException, AuthzException {
        authn();

        vertx.undeploy(profileVerticleId);
        context.assertEquals(500, request(context, "/profiles/1", TOKEN));
    }

    @Test
    public void error2(TestContext context) throws AuthnException, AuthzException {
        authn();

        vertx.undeploy(authVerticleId);
        context.assertEquals(500, request(context, "/profiles/1", TOKEN));
    }

}
