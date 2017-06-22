package com.irunninglog.vertx.profile;

import com.irunninglog.api.profile.IProfileService;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.mock.MockUser;
import com.irunninglog.vertx.AbstractTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class GetProfileTest extends AbstractTest {

    @Override
    protected void afterBefore(TestContext context) {
        super.afterBefore(context);

        IProfileService profileService = Mockito.mock(IProfileService.class);
        deploy(new GetProfileVerticle(factory(), mapper(), profileService), context);
    }

    @Test
    public void getProfile(TestContext context) throws AuthnException {
        returnFromAuthentication(new MockUser());

        assertEquals(200, get(context, "/api/profile"));
    }

}
