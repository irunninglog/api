package com.irunninglog.vertx.route;

import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.IAuthnRequest;
import com.irunninglog.api.security.AuthzException;
import com.irunninglog.profile.Profile;
import com.irunninglog.profile.ProfileRequest;
import com.irunninglog.profile.ProfileResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ResponseStatusException;
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
        vertx.deployVerticle(getProfileVerticle, context.asyncAssertSuccess(s -> {
            logger.info("Profile verticle deployed {}", s);

            profileVerticleId = s;
        }));
    }

    @Test
    public void ok(TestContext context) throws AuthnException, AuthzException {
        logger.info("ok");

        authn();

        Mockito.when(profileService.get(any(ProfileRequest.class)))
                .thenReturn(new ProfileResponse().setBody(new Profile()).setStatus(ResponseStatus.Ok));

        context.assertEquals(200, request(context, "/profiles/1", TOKEN));
    }

    @Test
    public void notFound(TestContext context) throws AuthnException, AuthzException {
        logger.info("notFound");

        authn();

        Mockito.when(profileService.get(any(ProfileRequest.class)))
                .thenThrow(new ResponseStatusException(ResponseStatus.NotFound));

        context.assertEquals(404, request(context, "/profiles/1", TOKEN));
    }

    @Test
    public void unauthenticated1(TestContext context) throws AuthnException, AuthzException {
        logger.info("unauthenticated1");

        Mockito.when(authenticationService.authenticate(any(IAuthnRequest.class))).thenThrow(new AuthnException("Unauthenticated"));

        context.assertEquals(401, request(context, "/profiles/1", ""));
    }

    @Test
    public void unauthenticated2(TestContext context) throws AuthnException, AuthzException {
        logger.info("unauthenticated2");

        Mockito.when(authenticationService.authenticate(any(IAuthnRequest.class))).thenThrow(new AuthnException("Unauthenticated"));

        context.assertEquals(401, request(context, "/profiles/1", TOKEN));
    }

    @Test
    public void unauthenticated3(TestContext context) throws AuthnException, AuthzException {
        logger.info("unauthenticated3");

        Mockito.when(authenticationService.authenticate(any(IAuthnRequest.class))).thenThrow(new AuthnException("Unauthenticated"));

        context.assertEquals(401, request(context, "/profiles/1", "Basic @@@"));
    }

    @Test
    public void error1(TestContext context) throws AuthnException, AuthzException {
        logger.info("error1");

        authn();

        vertx.undeploy(profileVerticleId);
        context.assertEquals(500, request(context, "/profiles/1", TOKEN));
    }

    @Test
    public void error2(TestContext context) throws AuthnException, AuthzException {
        logger.info("error2");

        authn();

        vertx.undeploy(authVerticleId);
        context.assertEquals(500, request(context, "/profiles/1", TOKEN));
    }

}
