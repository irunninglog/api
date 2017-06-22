package com.irunninglog.vertx.streaks;

import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.streaks.IStreaksService;
import com.irunninglog.mock.MockUser;
import com.irunninglog.vertx.AbstractTest;
import io.vertx.ext.unit.TestContext;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class GetStreaksTest extends AbstractTest {

    @Override
    protected void afterBefore(TestContext context) {
        super.afterBefore(context);

        IStreaksService streaksService = Mockito.mock(IStreaksService.class);
        deploy(new GetStreaksVerticle(factory(), mapper(), streaksService), context);
    }

    @Test
    public void getStreaks(TestContext context) throws AuthnException {
        returnFromAuthentication(new MockUser());

        assertEquals(200, get(context, "/api/streaks"));
    }

}
