package com.irunninglog.vertx.challenges;

import com.irunninglog.api.challenges.IChallengesService;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.mock.MockUser;
import com.irunninglog.vertx.AbstractTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class GetChallengesTest extends AbstractTest {

    @Override
    protected void afterBefore(TestContext context) {
        super.afterBefore(context);

        IChallengesService service = Mockito.mock(IChallengesService.class);

        deploy(new GetChallengesVerticle(factory(), mapper(), service), context);
    }

    @Test
    public void get(TestContext context) throws AuthnException {
        returnFromAuthentication(new MockUser());

        assertEquals(200, get(context, "/api/challenges"));
    }

}
