package com.irunninglog.vertx.route;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.profile.IGetProfileResponse;
import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.security.AuthzException;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.ResponseStatusException;
import com.irunninglog.vertx.mock.MockProfile;
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
        GetProfileVerticle getProfileVerticle = new GetProfileVerticle(factory, mapper, profileService);
        vertx.deployVerticle(getProfileVerticle, context.asyncAssertSuccess(s -> {
            logger.info("Profile verticle deployed {}", s);

            profileVerticleId = s;
        }));
    }

    @Test
    public void ok(TestContext context) throws AuthnException, AuthzException {
        logger.info("ok");

        authn();

        Mockito.when(profileService.get(any(Long.class)))
                .thenReturn(new MockProfile());

        context.assertEquals(200, get(context, "/profiles/1", TOKEN));
    }

    @Test
    public void notFound(TestContext context) throws AuthnException, AuthzException {
        logger.info("notFound");

        authn();

        Mockito.when(profileService.get(any(Long.class)))
                .thenThrow(new ResponseStatusException(ResponseStatus.NotFound));

        context.assertEquals(404, get(context, "/profiles/1", TOKEN));
    }

    @Test
    public void unauthenticated1(TestContext context) throws AuthnException, AuthzException {
        logger.info("unauthenticated1");

        Mockito.when(authenticationService.authenticate(any(Endpoint.class), any(String.class), any(String.class))).thenThrow(new AuthnException("Unauthenticated"));

        context.assertEquals(401, get(context, "/profiles/1", ""));
    }

    @Test
    public void unauthenticated2(TestContext context) throws AuthnException, AuthzException {
        logger.info("unauthenticated2");

        Mockito.when(authenticationService.authenticate(any(Endpoint.class), any(String.class), any(String.class))).thenThrow(new AuthnException("Unauthenticated"));

        context.assertEquals(401, get(context, "/profiles/1", TOKEN));
    }

    @Test
    public void unauthenticated3(TestContext context) throws AuthnException, AuthzException {
        logger.info("unauthenticated3");

        Mockito.when(authenticationService.authenticate(any(Endpoint.class), any(String.class), any(String.class))).thenThrow(new AuthnException("Unauthenticated"));

        context.assertEquals(401, get(context, "/profiles/1", "Basic @@@"));
    }

    @Test
    public void error1(TestContext context) throws AuthnException, AuthzException {
        logger.info("error1");

        authn();

        vertx.undeploy(profileVerticleId);
        context.assertEquals(500, get(context, "/profiles/1", TOKEN));
    }

    @Test
    public void error2(TestContext context) throws AuthnException, AuthzException {
        logger.info("error2");

        authn();

        vertx.undeploy(authVerticleId);
        context.assertEquals(500, get(context, "/profiles/1", TOKEN));
    }

    @Test
    public void error3(TestContext context) throws AuthnException, AuthzException {
        logger.info("error2");

        authn();

        vertx.undeploy(profileVerticleId);
        IMapper throwsMapper = Mockito.mock(IMapper.class);
        //noinspection unchecked
        Mockito.when(throwsMapper.decode(any(String.class), any(Class.class))).thenThrow(new IllegalArgumentException());
        vertx.deployVerticle(new GetProfileVerticle(factory, throwsMapper, profileService), context.asyncAssertSuccess());

        context.assertEquals(500, get(context, "/profiles/1", TOKEN));
    }

}
